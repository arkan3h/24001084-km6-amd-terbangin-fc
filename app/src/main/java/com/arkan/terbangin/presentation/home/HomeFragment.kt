package com.arkan.terbangin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseFragment
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.model.ContinentList
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.databinding.FragmentHomeBinding
import com.arkan.terbangin.presentation.flightdetail.FlightDetailActivity
import com.arkan.terbangin.presentation.flightsearch.FlightSearchActivity
import com.arkan.terbangin.presentation.home.adapter.ContinentAdapter
import com.arkan.terbangin.presentation.home.adapter.FlightRecommendationAdapter
import com.arkan.terbangin.presentation.home.calendar.calendardeparturedate.CalendarDepartureDateBottomSheet
import com.arkan.terbangin.presentation.home.calendar.calendarreturndate.CalendarReturnDateBottomSheet
import com.arkan.terbangin.presentation.home.class_sheet.ClassSheetFragment
import com.arkan.terbangin.presentation.home.common.HomeSaveButtonClickListener
import com.arkan.terbangin.presentation.home.passengers_count.PassengersCountBottomSheet
import com.arkan.terbangin.presentation.home.terminal_search.TerminalSearchBottomSheet
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.stringToLocalDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HomeFragment : BaseFragment(), HomeSaveButtonClickListener {
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var continentAdapter: ContinentAdapter
    private var flightAdapter: FlightRecommendationAdapter? = null
    private var status = "Return"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setState()
        getFlightRecommendation("Asia")
        observeContinentData()
        onClickListener()
        observeViewModel()
    }

    private fun observeContinentData() {
        continentAdapter =
            ContinentAdapter(
                listOf(
                    ContinentList("Asia", "Asia"),
                    ContinentList("Africa", "Afrika"),
                    ContinentList("America", "Amerika"),
                    ContinentList("Australia", "Australia"),
                    ContinentList("Europe", "Eropa"),
                ),
                object : OnItemCLickedListener<ContinentList> {
                    override fun onItemClicked(item: ContinentList) {
                        getFlightRecommendation(item.nameContinent)
                    }
                },
            )
        binding.rvContinent.apply {
            adapter = continentAdapter
        }
    }

    private fun getFlightRecommendation(continent: String) {
        viewModel.getFlightRecommendation(continent).observe(viewLifecycleOwner) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data ->
                        bindFlightRecommendation(data)
                    }
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    it.exception?.let { e -> handleError(e) }
                },
            )
        }
    }

    private fun bindFlightRecommendation(data: List<Flight>) {
        flightAdapter =
            FlightRecommendationAdapter(
                listener =
                    object : OnItemCLickedListener<Flight> {
                        override fun onItemClicked(item: Flight) {
                            onFlightSelected(item)
                        }
                    },
            )
        binding.rvDestination.adapter = this.flightAdapter
        flightAdapter?.submitData(data)
    }

    private fun onClickListener() {
        binding.layoutSearchHome.tvFlightFrom.setOnClickListener {
            openSearch("Pilih Lokasi Awal")
        }
        binding.layoutSearchHome.tvFlightTo.setOnClickListener {
            openSearch("Pilih Tujuan")
        }
        binding.layoutSearchHome.layoutPassengersSearch.layoutPassengersSearch.setOnClickListener {
            selectPassengers()
        }
        binding.layoutSearchHome.layoutSeatClassSearch.layoutSeatClassSearch.setOnClickListener {
            selectSeatClass()
        }
        binding.layoutSearchHome.btnSearchFlight.setOnClickListener {
            navigateToFlightSearch(
                viewModel.adultQty.value!!,
                viewModel.childrenQty.value!!,
                viewModel.babyQty.value!!,
                viewModel.totalQty.value!!,
                viewModel.ticketClass.value!!,
                status,
                viewModel.departureDate.value.toString(),
                viewModel.returnDate.value.toString(),
                viewModel.departureCity.value!!,
                viewModel.destinationCity.value!!,
            )
        }
        binding.layoutSearchHome.layoutDepartureSearch.layoutDepartureSearch.setOnClickListener {
            openDepartureDate()
        }
        binding.layoutSearchHome.layoutReturnSearch.layoutReturnSearch.setOnClickListener {
            openReturnDate()
        }
        binding.layoutSearchHome.switchDestination.setOnClickListener {
            switchDestinationCity()
        }
    }

    private fun setState() {
        binding.layoutSearchHome.switchRoundTrip.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutSearchHome.layoutReturnSearch.layoutReturnSearch.isVisible = isChecked
            status = if (isChecked) "Return" else "One Way"
            setState()
        }
        binding.layoutSearchHome.btnSearchFlight.isEnabled = false
        if (status == "One Way") {
            if (
                viewModel.totalQty.value != null &&
                viewModel.ticketClass.value != null &&
                viewModel.departureCity.value != null &&
                viewModel.destinationCity.value != null &&
                viewModel.departureDate.value != null
            ) {
                binding.layoutSearchHome.btnSearchFlight.isEnabled = true
            }
        } else if (status == "Return") {
            if (
                viewModel.totalQty.value != null &&
                viewModel.ticketClass.value != null &&
                viewModel.departureCity.value != null &&
                viewModel.destinationCity.value != null &&
                viewModel.departureDate.value != null &&
                viewModel.returnDate.value != null
            ) {
                binding.layoutSearchHome.btnSearchFlight.isEnabled = true
            }
        }
    }

    private fun openSearch(location: String) {
        val terminalSearchBottomSheet = TerminalSearchBottomSheet(location)
        terminalSearchBottomSheet.setCitySelectedListener(this)
        terminalSearchBottomSheet.show(parentFragmentManager, "TerminalSearchBottomSheet")
    }

    private fun openDepartureDate() {
        val calendarBottomSheet = CalendarDepartureDateBottomSheet()
        calendarBottomSheet.listener = this
        calendarBottomSheet.show(parentFragmentManager, "CalendarDepartureDateBottomSheet")
    }

    private fun openReturnDate() {
        val calendarBottomSheet = CalendarReturnDateBottomSheet()
        calendarBottomSheet.listener = this
        calendarBottomSheet.show(parentFragmentManager, "CalendarDepartureDateBottomSheet")
    }

    private fun selectPassengers() {
        val bottomSheet = PassengersCountBottomSheet()
        bottomSheet.listener = this
        bottomSheet.show(parentFragmentManager, "PassengersCountBottomSheet")
    }

    private fun selectSeatClass() {
        val classSheetFragment = ClassSheetFragment()
        classSheetFragment.listener = this
        classSheetFragment.show(parentFragmentManager, "ClassSheetFragment")
    }

    private fun observeViewModel() {
        viewModel.totalQty.observe(viewLifecycleOwner) { totalQty ->
            binding.layoutSearchHome.layoutPassengersSearch.tvResultPassengers.text =
                getString(R.string.text_binding_total_passenger, totalQty.toString())
        }
        viewModel.ticketClass.observe(viewLifecycleOwner) { ticketClass ->
            binding.layoutSearchHome.layoutSeatClassSearch.tvResultSeatClass.text = ticketClass.name
        }
        viewModel.departureDate.observe(viewLifecycleOwner) { date ->
            binding.layoutSearchHome.layoutDepartureSearch.tvResultDeparture.text = date.toString()
        }
        viewModel.returnDate.observe(viewLifecycleOwner) { date ->
            binding.layoutSearchHome.layoutReturnSearch.tvResultReturn.text = date.toString()
        }
        viewModel.departureCity.observe(viewLifecycleOwner) { city ->
            binding.layoutSearchHome.tvFlightFrom.text = getString(R.string.text_binding_airport_city, city.city, city.code)
        }
        viewModel.destinationCity.observe(viewLifecycleOwner) { city ->
            binding.layoutSearchHome.tvFlightTo.text = getString(R.string.text_binding_airport_city, city.city, city.code)
        }
    }

    private fun navigateToFlightSearch(
        adultQty: Int,
        childrenQty: Int,
        babyQty: Int,
        totalQty: Int,
        ticketClass: TicketClass,
        status: String,
        departureDate: String,
        returnDate: String?,
        departureCity: Airport,
        destinationCity: Airport,
    ) {
        val params =
            FlightSearchParams(
                adultQty,
                childrenQty,
                babyQty,
                totalQty,
                ticketClass,
                status,
                departureDate,
                returnDate,
                departureCity,
                destinationCity,
            )
        FlightSearchActivity.startActivity(
            requireContext(),
            params,
        )
    }

    private fun onFlightSelected(flight: Flight) {
        val departureDate = stringToLocalDate(flight.departureAt)
        viewModel.updateDepartureCity(
            Airport(
                flight.startAirportCity,
                flight.startAirportContinent,
                flight.startAirportCountry,
                flight.startAirportCode,
                flight.startAirportId,
                flight.startAirportName,
                flight.startAirportPicture,
                flight.startAirportTerminal,
            ),
        )
        viewModel.updateDestinationCity(
            Airport(
                flight.endAirportCity,
                flight.endAirportContinent,
                flight.endAirportCountry,
                flight.endAirportCode,
                flight.endAirportId,
                flight.endAirportName,
                flight.endAirportPicture,
                flight.endAirportTerminal,
            ),
        )
        viewModel.updateDepartureDate(departureDate)
        setPassengerDetailFlight(flight)
    }

    private fun setPassengerDetailFlight(flight: Flight) {
        val bottomSheet = PassengersCountBottomSheet()
        bottomSheet.listener = this
        bottomSheet.setOnDismissListener { setClassSeatDetailFlight(flight) }
        bottomSheet.show(parentFragmentManager, "PassengersCountBottomSheet")
    }

    private fun setClassSeatDetailFlight(flight: Flight) {
        val classSheetFragment = ClassSheetFragment()
        classSheetFragment.listener = this
        classSheetFragment.setOnDismissListener {
            if (
                viewModel.totalQty.value != null &&
                viewModel.ticketClass.value != null &&
                viewModel.departureCity.value != null &&
                viewModel.destinationCity.value != null &&
                viewModel.departureDate.value != null
            ) {
                navigateToFlightDetail(flight)
            }
        }
        classSheetFragment.show(parentFragmentManager, "ClassSheetFragment")
    }

    private fun navigateToFlightDetail(flight: Flight) {
        FlightDetailActivity.startActivity(
            requireContext(),
            FlightSearchParams(
                viewModel.adultQty.value!!,
                viewModel.childrenQty.value!!,
                viewModel.babyQty.value!!,
                viewModel.totalQty.value!!,
                viewModel.ticketClass.value!!,
                "One Way",
                viewModel.departureDate.value.toString(),
                viewModel.returnDate.value.toString(),
                viewModel.departureCity.value!!,
                viewModel.destinationCity.value!!,
            ),
            flight,
            null,
        )
    }

    override fun onPassengersCountUpdated(
        adultQty: Int,
        childrenQty: Int,
        babyQty: Int,
        totalQty: Int,
    ) {
        viewModel.updatePassengers(adultQty, childrenQty, babyQty, totalQty)
        setState()
    }

    override fun onClassSelected(ticketClass: TicketClass) {
        viewModel.updateTicketClass(ticketClass)
        setState()
    }

    override fun onDateDepartureSelected(date: LocalDate) {
        viewModel.updateDepartureDate(date)
        setState()
    }

    override fun onDateReturnSelected(date: LocalDate) {
        viewModel.updateReturnDate(date)
        setState()
    }

    private fun switchDestinationCity() {
        val departureCity = viewModel.departureCity.value
        val destinationCity = viewModel.destinationCity.value
        viewModel.updateDepartureCity(destinationCity!!)
        viewModel.updateDestinationCity(departureCity!!)
        setState()
    }

    override fun onCitySelected(
        city: Airport,
        location: String,
    ) {
        if (location == "Pilih Lokasi Awal") {
            viewModel.updateDepartureCity(city)
            setState()
        } else {
            viewModel.updateDestinationCity(city)
            setState()
        }
    }
}
