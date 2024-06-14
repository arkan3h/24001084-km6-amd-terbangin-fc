package com.arkan.terbangin.data.source.network.model.passanger

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PassengerResponse(
    @SerializedName("data")
    val data: PassengerResponseData?,
    @SerializedName("message")
    val message: String?,
)
