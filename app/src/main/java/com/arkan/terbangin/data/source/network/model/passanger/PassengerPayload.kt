package com.arkan.terbangin.data.source.network.model.passanger

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PassengerPayload(
    @SerializedName("birthDate")
    val birthDate: String?,
    @SerializedName("familyName")
    val familyName: Any?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("identityId")
    val identityId: String?,
    @SerializedName("issuingCountry")
    val issuingCountry: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("userId")
    val userId: String?,
)
