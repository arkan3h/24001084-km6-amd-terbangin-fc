package com.arkan.terbangin.data.datasource.airportcity

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.airport.AirportData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class AirportCityDataSourceImpl(
    private val services: TerbanginApiServices,
) : AirportCityDataSource {
    override suspend fun getAllAirportCity(): Response<List<AirportData>?> {
        return services.getAllAirport()
    }
}
