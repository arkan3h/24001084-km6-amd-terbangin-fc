package com.arkan.terbangin.data.source.network.model.booking2

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HelperBookingDataResponse(
    @SerializedName("bookingId")
    val bookingId: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("passangerId")
    val passangerId: String?,
    @SerializedName("seatId")
    val seatId: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
