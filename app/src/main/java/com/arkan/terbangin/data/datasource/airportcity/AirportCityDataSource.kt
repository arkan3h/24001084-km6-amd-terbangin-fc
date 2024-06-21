package com.arkan.terbangin.data.datasource.airportcity

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.airport.AirportData

interface AirportCityDataSource {
    suspend fun getAllAirportCity(): Response<List<AirportData>?>
}
