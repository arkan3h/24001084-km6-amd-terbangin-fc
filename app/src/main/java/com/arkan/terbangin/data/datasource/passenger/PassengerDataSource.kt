package com.arkan.terbangin.data.datasource.passenger

import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponse

interface PassengerDataSource {
    suspend fun createPassenger(passenger: PassengerPayload): PassengerResponse
}
