package com.arkan.terbangin.data.source.network.model.flight

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Airline(
    @SerializedName("additionals")
    val additionals: String?,
    @SerializedName("baggage")
    val baggage: Int?,
    @SerializedName("cabinBaggage")
    val cabinBaggage: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("serialNumber")
    val serialNumber: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
