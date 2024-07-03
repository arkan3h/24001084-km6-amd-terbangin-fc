package com.arkan.terbangin.data.source.network.model.booking

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BookingPayload(
    @SerializedName("paymentId")
    val paymentId: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("roundtripFlightId")
    val roundtripFlightId: String?,
)
