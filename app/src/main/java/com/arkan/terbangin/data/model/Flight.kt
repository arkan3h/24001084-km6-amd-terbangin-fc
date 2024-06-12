package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flight(
    val airlineAdditionals: String,
    val airlineBaggage: Int,
    val airlineCabinBaggage: Int,
    val airlineName: String,
    val airlinePicture: String,
    val airlineSerialNumber: String,
    val airlineId: String,
    val arrivalAt: String,
    val capacityBussines: Int,
    val capacityEconomy: Int,
    val capacityFirstClass: Int,
    val departureAt: String,
    val duration: Int,
    val endAirportCity: String,
    val endAirportContinent: String,
    val endAirportCountry: String,
    val endAirportName: String,
    val endAirportPicture: String,
    val endAirportTerminal: String,
    val endAirportId: String,
    val id: String,
    val priceBussines: Int,
    val priceEconomy: Int,
    val priceFirstClass: Int,
    val startAirportCity: String,
    val startAirportContinent: String,
    val startAirportCountry: String,
    val startAirportName: String,
    val startAirportPicture: String,
    val startAirportTerminal: String,
    val startAirportId: String,
) : Parcelable
