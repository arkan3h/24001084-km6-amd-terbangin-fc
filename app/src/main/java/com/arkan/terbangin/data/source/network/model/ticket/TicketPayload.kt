package com.arkan.terbangin.data.source.network.model.ticket

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TicketPayload(
    @SerializedName("bookingId")
    val bookingId: String?,
    @SerializedName("email")
    val email: String?,
)
