package com.arkan.terbangin.data.repository.airport

import com.arkan.terbangin.data.datasource.airportcity.AirportCityDataSource
import com.arkan.terbangin.data.mapper.toAirport
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AirportCityRepository {
    fun searchAirportCity(query: String): Flow<ResultWrapper<List<Airport>>>
}

class AirportCityRepositoryImpl(
    private val dataSource: AirportCityDataSource,
) : AirportCityRepository {
    override fun searchAirportCity(query: String): Flow<ResultWrapper<List<Airport>>> {
        return proceedFlow {
            dataSource.getAllAirportCity().data.toAirport().filter {
                it.city.contains(query, ignoreCase = true)
            }
        }
    }
}
