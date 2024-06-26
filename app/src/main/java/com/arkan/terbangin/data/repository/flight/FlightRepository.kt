package com.arkan.terbangin.data.repository.flight

import com.arkan.terbangin.data.datasource.flight.FlightDataSource
import com.arkan.terbangin.data.mapper.toFlight
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getAllFlight(
        start: String,
        end: String,
        key: String,
        value: String,
        filter: String,
        order: String,
        seatType: String,
    ): Flow<ResultWrapper<List<Flight>>>

    fun getFlightContinent(
        date: String,
        continent: String,
    ): Flow<ResultWrapper<List<Flight>>>
}

class FlightRepositoryImpl(
    private val dataSource: FlightDataSource,
) : FlightRepository {
    override fun getAllFlight(
        start: String,
        end: String,
        key: String,
        value: String,
        filter: String,
        order: String,
        seatType: String,
    ): Flow<ResultWrapper<List<Flight>>> {
        return proceedFlow {
            dataSource.getAllFlight(start, end, key, value, filter, order, seatType).data.toFlight()
        }
    }

    override fun getFlightContinent(
        date: String,
        continent: String,
    ): Flow<ResultWrapper<List<Flight>>> {
        return proceedFlow {
            dataSource.getFlightContinent(date, continent).data.toFlight()
        }
    }
}
