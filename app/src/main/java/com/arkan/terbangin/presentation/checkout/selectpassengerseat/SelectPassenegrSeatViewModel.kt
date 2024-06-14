package com.arkan.terbangin.presentation.checkout.selectpassengerseat

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList

class SelectPassenegrSeatViewModel(
    extras: Bundle?,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(SelectPassengerSeatActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(SelectPassengerSeatActivity.EXTRA_FLIGHT)
    val totalPrice = extras?.getDouble(SelectPassengerSeatActivity.EXTRA_TOTAL_PRICE)
    val passengerDataList = extras?.getParcelable<PassengerBioDataList>(SelectPassengerSeatActivity.EXTRA_PASSENGER_DATA)
    var capacity = 0

    init {
        capacity()
    }

    private fun capacity() {
        when (params?.ticketClass?.name) {
            "Economy" -> {
                capacity = flight?.capacityEconomy!!
            }
            "Business" -> {
                capacity = flight?.capacityBussines!!
            }
            "First Class" -> {
                capacity = flight?.capacityFirstClass!!
            }
        }
    }
}
