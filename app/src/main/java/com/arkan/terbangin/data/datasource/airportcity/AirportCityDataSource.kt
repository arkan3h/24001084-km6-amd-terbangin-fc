package com.arkan.terbangin.data.datasource.airportcity

import com.arkan.terbangin.data.source.network.model.airport.AirportResponse

interface AirportCityDataSource {
    suspend fun getAllAirportCity(): AirportResponse
}
