package com.arkan.terbangin.data.source.network.model.requestotp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestOTPResponse(
    @SerializedName("data")
    val data: RequestOTPData?,
    @SerializedName("message")
    val message: String?,
)
