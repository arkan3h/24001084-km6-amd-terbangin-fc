package com.arkan.terbangin.data.source.network.model.auth.resetpassword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResetPasswordResponse(
    @SerializedName("linkReset")
    val linkReset: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("token")
    val token: String?,
)
