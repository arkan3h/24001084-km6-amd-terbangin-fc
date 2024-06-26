package com.arkan.terbangin.data.datasource.booking2

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingPayload
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class HelperBookingApiDataSource2(
    private val services: TerbanginApiServices,
) : HelperBookingDataSource2 {
    override suspend fun createHelperBooking(payload: HelperBookingPayload): Response<HelperBookingDataResponse?> {
        return services.createHelperBooking(payload)
    }
}
