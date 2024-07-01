package com.arkan.terbangin.data.source.network.model.ticket

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("picture")
    val picture: Any?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
