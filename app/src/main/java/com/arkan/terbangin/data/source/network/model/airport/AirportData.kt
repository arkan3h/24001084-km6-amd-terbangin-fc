package com.arkan.terbangin.data.source.network.model.airport

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirportData(
    @SerializedName("city")
    val city: String?,
    @SerializedName("continent")
    val continent: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("iataCode")
    val iataCode: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("terminal")
    val terminal: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
