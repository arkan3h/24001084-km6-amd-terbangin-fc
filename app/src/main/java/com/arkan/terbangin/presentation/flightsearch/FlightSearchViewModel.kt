package com.arkan.terbangin.presentation.flightsearch

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.flight.FlightRepository
import com.arkan.terbangin.model.FilterList
import kotlinx.coroutines.Dispatchers

class FlightSearchViewModel(
    extras: Bundle?,
    private val flightRepository: FlightRepository,
) : ViewModel() {
    val extras = extras?.getParcelable<FlightSearchParams>(FlightSearchActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    private val _filter = MutableLiveData<FilterList>()
    val filter: LiveData<FilterList> get() = _filter
    private var filterName: String = ""
    private var filterOrder: String = ""

    fun updateTotalPrice(price: Double) {
        _totalPrice.value = price
    }

    fun filterList(filter: FilterList) {
        _filter.value = filter
        setFilterName(filter)
        setFilterOrder(filter)
    }

    private fun setFilterName(filter: FilterList) {
        when (filter.nameFilter) {
            "Harga" -> {
                when (extras?.ticketClass?.name) {
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
        start: String = extras?.departureCity?.city.orEmpty(),
        end: String = extras?.destinationCity?.city.orEmpty(),
        filter: String = filterName,
        order: String = filterOrder,
    ) = flightRepository.getAllFlight(start, end, filter, order).asLiveData(Dispatchers.IO)
}
