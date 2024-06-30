package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(
    var id: String,
    val bookingId: String,
    val userId: String,
    val flightId: String,
    val paymentId: String,
    val bookingStatus: String,
    val startLoc: String,
    val departureAt: String,
    val duration: Int,
    val endLoc: String,
    val arrivalAt: String,
    val bookingCode: String,
    val classes: String,
    val totalPayment: String,
    val monthHeader: String,
): Parcelable

@Parcelize
data class DetailHistory(
    var id: String,
    val bookingStatus: String,
    val bookingCode: String,
    val departureAt: String,
    val arrivalAt: String,
    val airportStart: String,
    val terminalStart: String,
    val airportEnd: String,
    val terminalEnd: String,
    val aircraftName: String,
    val classes: String,
    val aircraftType: String,
    val totalPrice: String,
    val passengerId: List<PassengerHistory>?,
): Parcelable

@Parcelize
data class PassengerHistory(
    var passengerId: String
): Parcelable