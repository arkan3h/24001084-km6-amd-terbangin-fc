package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeatList(
    val list: List<Seat>,
) : Parcelable

@Parcelize
data class Seat(
    val airlineClass: String,
    val flightId: String,
    val id: String,
    val isAvailable: Boolean,
    val seatNumber: Int,
) : Parcelable
