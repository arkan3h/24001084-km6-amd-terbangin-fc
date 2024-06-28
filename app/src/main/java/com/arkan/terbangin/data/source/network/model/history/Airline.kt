package com.arkan.terbangin.data.source.network.model.history


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Airline(
    @SerializedName("additionals")
    val additionals: String?,
    @SerializedName("aircraftType")
    val aircraftType: String?,
    @SerializedName("baggage")
    val baggage: Int?,
    @SerializedName("cabinBaggage")
    val cabinBaggage: Int?,
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
    @SerializedName("updatedAt")
    val updatedAt: String?
)