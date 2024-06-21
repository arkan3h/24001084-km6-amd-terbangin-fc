package com.arkan.terbangin.data.datasource.passenger

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponseData

interface PassengerDataSource {
    suspend fun createPassenger(passenger: PassengerPayload): Response<PassengerResponseData?>
}
