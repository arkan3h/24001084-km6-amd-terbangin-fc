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

    fun updateTotalPrice(price: Double) {
        _totalPrice.value = price
    }

    fun filterList(filter: FilterList) {
        _filter.value = filter
    }

    fun getAllFlight(
        start: String = extras?.departureCity?.city.orEmpty(),
        end: String = extras?.destinationCity?.city.orEmpty(),
    ) = flightRepository.getAllFlight(start, end).asLiveData(Dispatchers.IO)
}
