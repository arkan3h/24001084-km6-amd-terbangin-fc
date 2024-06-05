package com.arkan.terbangin.data.source.network.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
