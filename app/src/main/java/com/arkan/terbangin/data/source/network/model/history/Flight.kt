package com.arkan.terbangin.data.source.network.model.history


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Flight(
    @SerializedName("Airline")
    val airline: Airline?,
    @SerializedName("airlineId")
    val airlineId: String?,
    @SerializedName("arrivalAt")
    val arrivalAt: String?,
    @SerializedName("capacityBussines")
    val capacityBussines: Int?,
    @SerializedName("capacityEconomy")
    val capacityEconomy: Int?,
    @SerializedName("capacityFirstClass")
    val capacityFirstClass: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("departureAt")
    val departureAt: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("EndAirport")
    val endAirport: EndAirport?,
    @SerializedName("endAirportId")
    val endAirportId: String?,
    @SerializedName("flightCode")
    val flightCode: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("priceBussines")
    val priceBussines: Int?,
    @SerializedName("priceEconomy")
    val priceEconomy: Int?,
    @SerializedName("priceFirstClass")
    val priceFirstClass: Int?,
    @SerializedName("StartAirport")
    val startAirport: StartAirport?,
    @SerializedName("startAirportId")
    val startAirportId: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)