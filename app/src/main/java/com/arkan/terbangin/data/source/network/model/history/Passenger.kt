package com.arkan.terbangin.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Passanger(
    @SerializedName("birthDate")
    val birthDate: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("familyName")
    val familyName: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("identityId")
    val identityId: String?,
    @SerializedName("identityIdExpired")
    val identityIdExpired: Any?,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: String?,
)
