package com.arkan.terbangin.data.datasource.flight

import com.arkan.terbangin.data.source.network.model.flight.FlightResponse

interface FlightDataSource {
    suspend fun getAllFlight(
        start: String,
        end: String,
        key: String,
        value: String,
        filter: String,
        order: String,
        seatType: String,
    ): FlightResponse
}
