package com.arkan.terbangin.data.repository

import com.arkan.terbangin.data.datasource.airportcity.AirportCityDataSource
import com.arkan.terbangin.data.model.AirportCity
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AirportCityRepository {
    fun getAllAirportCity(): Flow<ResultWrapper<List<AirportCity>>>

    fun searchAirportCity(query: String): Flow<ResultWrapper<List<AirportCity>>>
}

class AirportCityRepositoryImpl(
    private val dataSource: AirportCityDataSource,
) : AirportCityRepository {
    override fun getAllAirportCity(): Flow<ResultWrapper<List<AirportCity>>> {
        return proceedFlow {
            dataSource.getAllAirportCity()
        }
    }

    override fun searchAirportCity(query: String): Flow<ResultWrapper<List<AirportCity>>> {
        return proceedFlow {
            dataSource.getAllAirportCity().filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}
