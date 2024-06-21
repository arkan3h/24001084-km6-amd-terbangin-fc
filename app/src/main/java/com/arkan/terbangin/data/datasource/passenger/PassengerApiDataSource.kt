package com.arkan.terbangin.data.datasource.passenger

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponseData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class PassengerApiDataSource(
    private val services: TerbanginApiServices,
) : PassengerDataSource {
    override suspend fun createPassenger(passenger: PassengerPayload): Response<PassengerResponseData?> {
        return services.createPassenger(passenger)
    }
}
