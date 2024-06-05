package com.arkan.terbangin.data.source.network.model.auth.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginUser(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
