package com.arkan.terbangin.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HelperBookingResponseData(
    @SerializedName("Booking")
    val booking: Booking?,
    @SerializedName("bookingId")
    val bookingId: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("Passanger")
    val passanger: Passanger?,
    @SerializedName("passangerId")
    val passangerId: String?,
    @SerializedName("Seat")
    val seat: Seat?,
    @SerializedName("seatId")
    val seatId: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
