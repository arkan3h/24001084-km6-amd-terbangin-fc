package com.arkan.terbangin.data.source.network.model.ticket

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TicketDataResponse(
    @SerializedName("bookingCode")
    val bookingCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("Payment")
    val payment: Payment?,
    @SerializedName("paymentId")
    val paymentId: String?,
    @SerializedName("roundtripFlightId")
    val roundtripFlightId: Any?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("User")
    val user: User?,
    @SerializedName("userId")
    val userId: String?,
)
