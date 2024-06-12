package com.arkan.terbangin.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.AirportCity
import com.arkan.terbangin.data.model.TicketClass
import java.time.LocalDate

class HomeViewModel : ViewModel() {
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

    private val _departureCity = MutableLiveData<AirportCity>()
    val departureCity: LiveData<AirportCity> get() = _departureCity

    private val _destinationCity = MutableLiveData<AirportCity>()
    val destinationCity: LiveData<AirportCity> get() = _destinationCity

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

    fun updateTicketClass(ticketClass: TicketClass) {
        _ticketClass.value = ticketClass
    }

    fun updateDepartureDate(date: LocalDate) {
        _departureDate.value = date
    }

    fun updateReturnDate(date: LocalDate) {
        _returnDate.value = date
    }

    fun updateDepartureCity(city: AirportCity) {
        _departureCity.value = city
    }

    fun updateDestinationCity(city: AirportCity) {
        _destinationCity.value = city
    }
}
