package com.arkan.terbangin.presentation.flightsearch.flightreturnsearch

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.FilterList
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.flight.FlightRepository
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate

class FlightSearchReturnViewModel(
    extras: Bundle?,
    private val flightRepository: FlightRepository,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(FlightSearchReturnActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(FlightSearchReturnActivity.EXTRA_FLIGHT)

    private val _filter = MutableLiveData<FilterList>()
    val filter: LiveData<FilterList> get() = _filter
    private var filterName: String = ""
    private var filterOrder: String = ""
    private var filterSeat: String = ""
    private val _date = MutableLiveData(params?.returnDate.orEmpty())
    val date: LocalDate get() = LocalDate.parse(params?.returnDate)

    fun filterList(filter: FilterList) {
        _filter.value = filter
        setFilterName(filter)
        setFilterOrder(filter)
    }

    private fun setFilterName(filter: FilterList) {
        when (filter.nameFilter) {
            "Harga" -> {
                when (params?.ticketClass?.name) {
                    "Economy" -> {
                        filterName = "priceEconomy"
                        filterSeat = "Economy"
                    }
                    "Business" -> {
                        filterName = "priceBussines"
                        filterSeat = "Bussines"
                    }
                    "First Class" -> {
                        filterName = "priceFirstClass"
                        filterSeat = "Firstclass"
                    }
                }
            }
            "Durasi" -> {
                filterName = "duration"
            }
            "Keberangkatan" -> {
                filterName = "departureAt"
            }
            "Kedatangan" -> {
                filterName = "arrivalAt"
            }
        }
    }

    private fun setFilterOrder(filter: FilterList) {
        if (filter.listFilter == "Termurah" || filter.listFilter == "Terpendek" || filter.listFilter == "Paling Awal") {
            filterOrder = "asc"
        } else if (filter.listFilter == "Termahal" || filter.listFilter == "Terpanjang" || filter.listFilter == "Paling Akhir") {
            filterOrder = "desc"
        }
    }

    fun getAllFlight(
        start: String = params?.destinationCity?.city.orEmpty(),
        end: String = params?.departureCity?.city.orEmpty(),
        key: String = "departureAt",
        value: String = _date.value.orEmpty(),
        filter: String = filterName,
        order: String = filterOrder,
        seatType: String = filterSeat,
    ) = flightRepository.getAllFlight(start, end, key, value, filter, order, seatType).asLiveData(Dispatchers.IO)

    fun updateDate(newDate: String) {
        _date.value = newDate
    }
}
