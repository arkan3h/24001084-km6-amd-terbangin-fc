package com.arkan.terbangin.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HelperBookingResponse(
    @SerializedName("data")
    val data: List<HelperBookingResponseData>?,
    @SerializedName("message")
    val message: String?,
)
