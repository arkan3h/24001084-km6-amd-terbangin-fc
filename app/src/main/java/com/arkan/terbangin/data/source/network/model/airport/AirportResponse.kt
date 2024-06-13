package com.arkan.terbangin.data.source.network.model.airport

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirportResponse(
    @SerializedName("data")
    val data: List<AirportData>?,
    @SerializedName("message")
    val message: String?,
)
