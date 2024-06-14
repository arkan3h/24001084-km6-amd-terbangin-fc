package com.arkan.terbangin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.databinding.FragmentHomeBinding
import com.arkan.terbangin.presentation.flightsearch.FlightSearchActivity
import com.arkan.terbangin.presentation.home.calendar.calendardeparturedate.CalendarDepartureDateBottomSheet
import com.arkan.terbangin.presentation.home.calendar.calendarreturndate.CalendarReturnDateBottomSheet
import com.arkan.terbangin.presentation.home.class_sheet.ClassSheetFragment
import com.arkan.terbangin.presentation.home.common.HomeSaveButtonClickListener
import com.arkan.terbangin.presentation.home.passengers_count.PassengersCountBottomSheet
import com.arkan.terbangin.presentation.home.terminal_search.TerminalSearchBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HomeFragment : Fragment(), HomeSaveButtonClickListener {
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding

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
        setRoundTrip()
        onClickListener()
        observeViewModel()
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
            if (
                viewModel.totalQty.value != null &&
                viewModel.ticketClass.value != null &&
                viewModel.departureCity.value != null &&
                viewModel.destinationCity.value != null &&
                viewModel.departureDate.value != null
            ) {
                navigateToFlightSearch(
                    viewModel.adultQty.value!!,
                    viewModel.childrenQty.value!!,
                    viewModel.babyQty.value!!,
                    viewModel.totalQty.value!!,
                    viewModel.ticketClass.value!!,
                    viewModel.departureDate.value.toString(),
                    viewModel.returnDate.value.toString(),
                    viewModel.departureCity.value!!,
                    viewModel.destinationCity.value!!,
                )
            }
        }
        binding.layoutSearchHome.layoutDepartureSearch.layoutDepartureSearch.setOnClickListener {
            openDepartureDate()
        }
        binding.layoutSearchHome.layoutReturnSearch.layoutReturnSearch.setOnClickListener {
            openReturnDate()
        }
    }

    private fun setRoundTrip() {
        binding.layoutSearchHome.switchRoundTrip.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutSearchHome.layoutReturnSearch.layoutReturnSearch.isVisible = isChecked
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

    override fun onPassengersCountUpdated(
        adultQty: Int,
        childrenQty: Int,
        babyQty: Int,
        totalQty: Int,
    ) {
        viewModel.updatePassengers(adultQty, childrenQty, babyQty, totalQty)
    }

    override fun onClassSelected(ticketClass: TicketClass) {
        viewModel.updateTicketClass(ticketClass)
    }

    override fun onDateDepartureSelected(date: LocalDate) {
        viewModel.updateDepartureDate(date)
    }

    override fun onDateReturnSelected(date: LocalDate) {
        viewModel.updateReturnDate(date)
    }

    override fun onCitySelected(
        city: Airport,
        location: String,
    ) {
        if (location == "Pilih Lokasi Awal") {
            viewModel.updateDepartureCity(city)
        } else {
            viewModel.updateDestinationCity(city)
        }
    }
}
