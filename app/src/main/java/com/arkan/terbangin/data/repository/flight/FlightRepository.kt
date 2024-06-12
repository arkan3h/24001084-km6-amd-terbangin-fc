package com.arkan.terbangin.data.repository.flight

import com.arkan.terbangin.data.datasource.flight.FlightDataSource
import com.arkan.terbangin.data.mapper.toFlight
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getAllFlight(): Flow<ResultWrapper<List<Flight>>>
}

class FlightRepositoryImpl(
    private val dataSource: FlightDataSource,
) : FlightRepository {
    override fun getAllFlight(): Flow<ResultWrapper<List<Flight>>> {
        return proceedFlow {
            dataSource.getAllFlight().data.toFlight()
        }
    }
}