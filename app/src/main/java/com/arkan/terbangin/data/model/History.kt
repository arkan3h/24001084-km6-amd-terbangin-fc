package com.arkan.terbangin.data.model

import java.util.UUID

data class History(
    var id: String = UUID.randomUUID().toString(),
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
    val totalPayment: String
)