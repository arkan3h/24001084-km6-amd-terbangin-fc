package com.arkan.terbangin.data.datasource.passenger

import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class PassengerApiDataSource(
    private val services: TerbanginApiServices,
) : PassengerDataSource {
    override suspend fun createPassenger(passenger: PassengerPayload): PassengerResponse {
        return services.createPassenger(passenger)
    }
}
