package com.arkan.terbangin.data.datasource.airportcity

import com.arkan.terbangin.data.source.network.model.airport.AirportResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class AirportCityDataSourceImpl(
    private val services: TerbanginApiServices,
) : AirportCityDataSource {
    override suspend fun getAllAirportCity(): AirportResponse {
        return services.getAllAirport()
    }
}
