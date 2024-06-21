package com.arkan.terbangin.data.source.network.model.seat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeatData(
    @SerializedName("airlineClass")
    val airlineClass: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("flightId")
    val flightId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isAvailable")
    val isAvailable: Boolean?,
    @SerializedName("seatNumber")
    val seatNumber: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
