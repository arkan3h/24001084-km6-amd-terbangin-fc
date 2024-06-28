package com.arkan.terbangin.data.source.network.model.history


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class HelperBookingResponse(
    @SerializedName("data")
    val data: List<HelperBookingResponseData>?,
    @SerializedName("message")
    val message: String?
)