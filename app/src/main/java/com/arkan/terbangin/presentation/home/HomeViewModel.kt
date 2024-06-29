package com.arkan.terbangin.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.data.repository.flight.FlightRepository
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate

class HomeViewModel(
    private val repository: FlightRepository,
) : ViewModel() {
    private val _adultQty = MutableLiveData<Int>()
    val adultQty: LiveData<Int> get() = _adultQty

    private val _childrenQty = MutableLiveData<Int>()
    val childrenQty: LiveData<Int> get() = _childrenQty

    private val _babyQty = MutableLiveData<Int>()
    val babyQty: LiveData<Int> get() = _babyQty

    private val _totalQty = MutableLiveData<Int>()
    val totalQty: LiveData<Int> get() = _totalQty

    private val _ticketClass = MutableLiveData<TicketClass>()
    val ticketClass: LiveData<TicketClass> get() = _ticketClass

    private val _departureDate = MutableLiveData<LocalDate>()
    val departureDate: LiveData<LocalDate> get() = _departureDate

    private val _returnDate = MutableLiveData<LocalDate>()
    val returnDate: LiveData<LocalDate> get() = _returnDate

    private val _departureCity = MutableLiveData<Airport>()
    val departureCity: LiveData<Airport> get() = _departureCity

    private val _destinationCity = MutableLiveData<Airport>()
    val destinationCity: LiveData<Airport> get() = _destinationCity

    private val date = LocalDate.now().plusDays(10).toString()

    fun updatePassengers(
        adult: Int,
        children: Int,
        baby: Int,
        total: Int,
    ) {
        _adultQty.value = adult
        _childrenQty.value = children
        _babyQty.value = baby
        _totalQty.value = total
    }

    fun getFlightRecommendation(continent: String) = repository.getFlightContinent(date, continent).asLiveData(Dispatchers.IO)

    fun updateTicketClass(ticketClass: TicketClass) {
        _ticketClass.value = ticketClass
    }

    fun updateDepartureDate(date: LocalDate) {
        _departureDate.value = date
    }

    fun updateReturnDate(date: LocalDate) {
        _returnDate.value = date
    }

    fun updateDepartureCity(city: Airport) {
        _departureCity.value = city
    }

    fun updateDestinationCity(city: Airport) {
        _destinationCity.value = city
    }
}
