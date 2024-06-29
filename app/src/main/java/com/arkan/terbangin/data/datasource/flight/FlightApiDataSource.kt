package com.arkan.terbangin.data.datasource.flight

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.flight.FlightDataResponse
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
        seatType: String,
    ): Response<List<FlightDataResponse>?> {
        return services.getAllFlight(start, end, key, value, filter, order, seatType)
    }

    override suspend fun getFlightContinent(
        date: String,
        continent: String,
    ): Response<List<FlightDataResponse>?> {
        return services.getFlightContinent(date, continent)
    }
}
