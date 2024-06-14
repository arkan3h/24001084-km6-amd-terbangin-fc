package com.arkan.terbangin.data.repository.passenger

import com.arkan.terbangin.data.datasource.passenger.PassengerDataSource
import com.arkan.terbangin.data.mapper.toBioData
import com.arkan.terbangin.data.mapper.toPayload
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PassengerRepository {
    fun createPassenger(passenger: PassengerBioData): Flow<ResultWrapper<PassengerBioData>>
}

class PassengerRepositoryImpl(
    private val dataSource: PassengerDataSource,
) : PassengerRepository {
    override fun createPassenger(passenger: PassengerBioData): Flow<ResultWrapper<PassengerBioData>> {
        return proceedFlow {
            dataSource.createPassenger(passenger.toPayload()).toBioData()
        }
    }
}
