package com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyOTPResponse(
    @SerializedName("data")
    val data: VerifyOTPData?,
    @SerializedName("message")
    val message: String?,
)
