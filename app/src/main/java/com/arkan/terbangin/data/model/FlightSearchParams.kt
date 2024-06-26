package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightSearchParams(
    val adultQty: Int,
    val childrenQty: Int,
    val babyQty: Int,
    val totalQty: Int,
    val ticketClass: TicketClass,
    val status: String,
    val departureDate: String,
    val returnDate: String?,
    val departureCity: Airport,
    val destinationCity: Airport,
) : Parcelable
