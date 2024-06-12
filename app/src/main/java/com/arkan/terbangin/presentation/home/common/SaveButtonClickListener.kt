package com.arkan.terbangin.presentation.home.common

import com.arkan.terbangin.data.model.AirportCity
import com.arkan.terbangin.data.model.TicketClass
import java.time.LocalDate

interface SaveButtonClickListener {
    fun onDateDepartureSelected(date: LocalDate)

    fun onDateReturnSelected(date: LocalDate)

    fun onPassengersCountUpdated(
        adultQty: Int,
        childrenQty: Int,
        babyQty: Int,
        totalQty: Int,
    )

    fun onClassSelected(ticketClass: TicketClass)

    fun onCitySelected(
        city: AirportCity,
        location: String,
    )
}
