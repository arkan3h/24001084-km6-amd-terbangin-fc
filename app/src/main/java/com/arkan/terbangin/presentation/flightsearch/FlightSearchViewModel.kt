package com.arkan.terbangin.presentation.flightsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.flight.FlightRepository
import kotlinx.coroutines.Dispatchers

class FlightSearchViewModel(
    private val flightRepository: FlightRepository,
) : ViewModel() {
    fun getAllFlight() = flightRepository.getAllFlight().asLiveData(Dispatchers.IO)
}
