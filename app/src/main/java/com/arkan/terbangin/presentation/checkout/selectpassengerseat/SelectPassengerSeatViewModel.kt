package com.arkan.terbangin.presentation.checkout.selectpassengerseat

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.data.model.Seat
import com.arkan.terbangin.data.repository.seat.SeatRepository
import kotlinx.coroutines.Dispatchers

class SelectPassengerSeatViewModel(
    extras: Bundle?,
    private val repository: SeatRepository,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(SelectPassengerSeatActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(SelectPassengerSeatActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(SelectPassengerSeatActivity.EXTRA_FLIGHT_RETURN)
    val totalPrice = extras?.getDouble(SelectPassengerSeatActivity.EXTRA_TOTAL_PRICE)
    val passengerDataList = extras?.getParcelable<PassengerBioDataList>(SelectPassengerSeatActivity.EXTRA_PASSENGER_DATA)
    var selectableSeat = 0
    var capacity = 0
    var seatCapacity = 0
    private var seatClass: String = ""

    val seat = MutableLiveData<List<Seat>>()
    val seats: LiveData<List<Seat>> = seat

    init {
        capacity()
        seatClass()
    }

    private fun capacity() {
        when (params?.ticketClass?.name) {
            "Economy" -> {
                capacity = flight?.capacityEconomy!!
                seatCapacity = 66
            }
            "Business" -> {
                capacity = flight?.capacityBussines!!
                seatCapacity = 18
            }
            "First Class" -> {
                capacity = flight?.capacityFirstClass!!
                seatCapacity = 6
            }
        }
        selectableSeat = params?.totalQty!! - params.babyQty
    }

    private fun seatClass() {
        when (params?.ticketClass?.name) {
            "Economy" -> {
                seatClass = "ECONOMY"
            }
            "Business" -> {
                seatClass = "BUSINESS"
            }
            "First Class" -> {
                seatClass = "FIRST_CLASS"
            }
        }
    }

    fun getSeat(
        id: String = flight?.id.toString(),
        filter: String = seatClass,
    ) = repository.getSeat(id, filter).asLiveData(Dispatchers.IO)
}
