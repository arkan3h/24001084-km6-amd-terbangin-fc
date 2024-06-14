package com.arkan.terbangin.presentation.flightsearch

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.FilterList
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.flight.FlightRepository
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate

class FlightSearchViewModel(
    extras: Bundle?,
    private val flightRepository: FlightRepository,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(FlightSearchActivity.EXTRA_FLIGHT_SEARCH_PARAMS)

    private val _filter = MutableLiveData<FilterList>()
    val filter: LiveData<FilterList> get() = _filter
    private var filterName: String = ""
    private var filterOrder: String = ""
    private val _date = MutableLiveData(params?.departureDate.orEmpty())
    val date: LocalDate get() = LocalDate.parse(params?.departureDate)

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
                    }
                    "Business" -> {
                        filterName = "priceBussines"
                    }
                    "First Class" -> {
                        filterName = "priceFirstClass"
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
        start: String = params?.departureCity?.city.orEmpty(),
        end: String = params?.destinationCity?.city.orEmpty(),
        key: String = "departureAt",
        value: String = _date.value.orEmpty(),
        filter: String = filterName,
        order: String = filterOrder,
    ) = flightRepository.getAllFlight(start, end, key, value, filter, order).asLiveData(Dispatchers.IO)

    fun updateDate(newDate: String) {
        _date.value = newDate
    }
}
