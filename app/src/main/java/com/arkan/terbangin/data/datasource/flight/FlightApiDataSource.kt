package com.arkan.terbangin.data.datasource.flight

import com.arkan.terbangin.data.source.network.model.flight.FlightResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class FlightApiDataSource(
    private val services: TerbanginApiServices,
) : FlightDataSource {
    override suspend fun getAllFlight(
        start: String,
        end: String,
        key: String,
        value: String,
        filter: String,
        order: String,
    ): FlightResponse {
        return services.getAllFlight(start, end, key, value, filter, order)
    }
}
