package com.arkan.terbangin.data.source.network.model.flight

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StartAirport(
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
