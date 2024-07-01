package com.arkan.terbangin.data.source.network.model.booking

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BookingDataResponse(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("paymentId")
    val paymentId: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: String?,
)
