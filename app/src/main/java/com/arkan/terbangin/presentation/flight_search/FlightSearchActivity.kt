package com.arkan.terbangin.presentation.flight_search

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityFlightSearchBinding
import com.arkan.terbangin.databinding.LayoutCalendarDaySliderBinding
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class FlightSearchActivity : AppCompatActivity() {
    private val binding: ActivityFlightSearchBinding by lazy {
        ActivityFlightSearchBinding.inflate(layoutInflater)
    }

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
        setClickListener()
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

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
