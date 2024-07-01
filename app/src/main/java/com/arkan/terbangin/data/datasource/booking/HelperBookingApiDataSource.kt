package com.arkan.terbangin.data.datasource.booking

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingPayload
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class HelperBookingApiDataSource(
    private val services: TerbanginApiServices,
) : HelperBookingDataSource {
    override suspend fun createHelperBooking(payload: HelperBookingPayload): Response<HelperBookingDataResponse?> {
        return services.createHelperBooking(payload)
    }
}
