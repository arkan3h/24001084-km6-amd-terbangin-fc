package com.arkan.terbangin.data.source.network.model.flight

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlightResponse(
    @SerializedName("data")
    val data: List<FlightDataResponse>?,
    @SerializedName("message")
    val message: String?,
)
