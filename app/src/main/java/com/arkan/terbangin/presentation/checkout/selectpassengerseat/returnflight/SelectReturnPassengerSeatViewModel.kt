package com.arkan.terbangin.presentation.checkout.selectpassengerseat.returnflight

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.data.model.Seat
import com.arkan.terbangin.data.model.SeatList
import com.arkan.terbangin.data.repository.seat.SeatRepository
import kotlinx.coroutines.Dispatchers

class SelectReturnPassengerSeatViewModel(
    extras: Bundle?,
    private val repository: SeatRepository,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(SelectReturnPassengerSeatActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(SelectReturnPassengerSeatActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(SelectReturnPassengerSeatActivity.EXTRA_FLIGHT_RETURN)
    val totalPrice = extras?.getDouble(SelectReturnPassengerSeatActivity.EXTRA_TOTAL_PRICE)
    val passengerDataList = extras?.getParcelable<PassengerBioDataList>(SelectReturnPassengerSeatActivity.EXTRA_PASSENGER_DATA)
    val seatDeparture = extras?.getParcelable<SeatList>(SelectReturnPassengerSeatActivity.EXTRA_SEAT)
    var selectableSeat = 0
    var capacity = 0
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
                capacity = flightReturn?.capacityEconomy!!
            }
            "Business" -> {
                capacity = flightReturn?.capacityBussines!!
            }
            "First Class" -> {
                capacity = flightReturn?.capacityFirstClass!!
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
        id: String = flightReturn?.id.toString(),
        filter: String = seatClass,
    ) = repository.getSeat(id, filter).asLiveData(Dispatchers.IO)
}
