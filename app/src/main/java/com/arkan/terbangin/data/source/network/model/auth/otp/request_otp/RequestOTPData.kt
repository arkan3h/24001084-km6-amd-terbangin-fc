package com.arkan.terbangin.data.source.network.model.auth.otp.request_otp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestOTPData(
    @SerializedName("code")
    val code: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("expire")
    val expire: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isUsed")
    val isUsed: Boolean?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
