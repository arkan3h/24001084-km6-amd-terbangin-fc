package com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyOTPData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("expire")
    val expire: String?,
    @SerializedName("isUsed")
    val isUsed: Boolean?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
