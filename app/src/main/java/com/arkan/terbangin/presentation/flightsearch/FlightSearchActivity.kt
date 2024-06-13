package com.arkan.terbangin.presentation.flightsearch

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.databinding.ActivityFlightSearchBinding
import com.arkan.terbangin.databinding.LayoutCalendarDaySliderBinding
import com.arkan.terbangin.model.FilterList
import com.arkan.terbangin.presentation.flightdetail.FlightDetailActivity
import com.arkan.terbangin.presentation.flightsearch.adapter.FlightAdapter
import com.arkan.terbangin.presentation.flightsearch.filter_list.FilterClickListener
import com.arkan.terbangin.presentation.flightsearch.filter_list.FilterListFragment
import com.arkan.terbangin.utils.proceedWhen
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class FlightSearchActivity : AppCompatActivity(), FilterClickListener {
    private val binding: ActivityFlightSearchBinding by lazy {
        ActivityFlightSearchBinding.inflate(layoutInflater)
    }
    private val viewModel: FlightSearchViewModel by viewModel {
        parametersOf(intent.extras)
    }
    private var flightAdapter: FlightAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.flight_search)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val isDarkTheme = uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
        ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = isDarkTheme
        window.statusBarColor = this.resources.getColor(R.color.md_theme_primary, theme)
        setDaySlider()
        observeViewModel()
        setClickListener()
        getFlightData()
    }

    private fun setDaySlider() {
        var selectedDate = LocalDate.now()
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = LayoutCalendarDaySliderBinding.bind(view)
            lateinit var day: WeekDay

            init {
                view.setOnClickListener {
                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.cvDaySlider.notifyDateChanged(day.date)
                        oldDate?.let { binding.cvDaySlider.notifyDateChanged(it) }
                    }
                }
            }

            fun bind(day: WeekDay) {
                this.day = day
                bind.tvSelectedDate.text = dateFormatter.format(day.date)
                bind.tvSelectedDay.text = day.date.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale("id", "ID"))

                val colorRes =
                    if (day.date == selectedDate) {
                        R.color.md_theme_onPrimary
                    } else {
                        R.color.md_theme_onSurface
                    }
                bind.tvSelectedDate.setTextColor(view.context.getColor(colorRes))
                bind.tvSelectedDay.setTextColor(view.context.getColor(colorRes))
                bind.vSelectedBg.isVisible = day.date == selectedDate
            }
        }

        binding.cvDaySlider.dayBinder =
            object : WeekDayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view)

                override fun bind(
                    container: DayViewContainer,
                    data: WeekDay,
                ) = container.bind(data)
            }

        val currentMonth = YearMonth.now()
        binding.cvDaySlider.setup(
            currentMonth.minusMonths(5).atStartOfMonth(),
            currentMonth.plusMonths(5).atEndOfMonth(),
            firstDayOfWeekFromLocale(),
        )
        binding.cvDaySlider.scrollToDate(LocalDate.now())
    }

    private fun observeViewModel() {
        binding.layoutAppBar.tvAppbarTitle.text =
            getString(
                R.string.text_binding_flight_search_title,
                viewModel.extras?.departureCity?.code,
                viewModel.extras?.destinationCity?.code,
                viewModel.extras?.totalQty.toString(),
                viewModel.extras?.ticketClass?.name,
            )
        viewModel.filter.observe(this) { filter ->
            binding.tvFilter.text = filter.nameFilter
            binding.tvSort.text = filter.listFilter
        }
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnFilter.setOnClickListener {
            selectFilter()
        }
    }

    private fun getFlightData() {
        viewModel.getAllFlight().observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvDestination.isVisible = false
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.rvDestination.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvDestination.isVisible = true
                    it.payload?.let { data ->
                        bindFlightList(data)
                    }
                },
                doOnEmpty = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.rvDestination.isVisible = false
                },
            )
        }
    }

    private fun bindFlightList(data: List<Flight>) {
        flightAdapter =
            FlightAdapter(
                listener =
                    object : OnItemCLickedListener<Flight> {
                        override fun onItemClicked(item: Flight) {
                            navigateToFlightDetail(item, viewModel.extras!!)
                        }
                    },
                viewModel.extras?.totalQty!!,
                viewModel.extras?.ticketClass!!.name,
            )
        binding.rvDestination.adapter = this.flightAdapter
        flightAdapter?.submitData(data)
    }

    private fun selectFilter() {
        val filterListFragment = FilterListFragment()
        filterListFragment.listener = this
        filterListFragment.show(supportFragmentManager, "FilterListFragment")
    }

    fun navigateToFlightDetail(
        item: Flight,
        extras: FlightSearchParams,
    ) {
        FlightDetailActivity.startActivity(
            this,
            extras,
            item,
        )
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
        ) {
            val intent = Intent(context, FlightSearchActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            context.startActivity(intent)
        }
    }

    override fun onFilterSelected(filter: FilterList) {
        viewModel.filterList(filter)
        getFlightData()
    }
}
