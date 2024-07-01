package com.arkan.terbangin.data.source.network.model.booking

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HelperBookingPayload(
    @SerializedName("bookingId")
    val bookingId: String?,
    @SerializedName("passangerId")
    val passangerId: String?,
    @SerializedName("seatId")
    val seatId: String?,
)
