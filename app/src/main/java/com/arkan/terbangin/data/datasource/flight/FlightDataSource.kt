package com.arkan.terbangin.data.datasource.flight

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.flight.FlightDataResponse

interface FlightDataSource {
    suspend fun getAllFlight(
        start: String,
        end: String,
        key: String,
        value: String,
        filter: String,
        order: String,
        seatType: String,
    ): Response<List<FlightDataResponse>?>
}
