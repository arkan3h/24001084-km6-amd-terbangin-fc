package com.arkan.terbangin.presentation.flightsearch

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.flight.FlightRepository
import kotlinx.coroutines.Dispatchers

class FlightSearchViewModel(
    extras: Bundle?,
    private val flightRepository: FlightRepository,
) : ViewModel() {
    val extras = extras?.getParcelable<FlightSearchParams>(FlightSearchActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun updateTotalPrice(price: Double) {
        _totalPrice.value = price
    }

    fun getAllFlight() = flightRepository.getAllFlight().asLiveData(Dispatchers.IO)
}
