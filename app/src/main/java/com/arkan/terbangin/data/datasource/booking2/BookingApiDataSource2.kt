package com.arkan.terbangin.data.datasource.booking2

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking2.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking2.BookingPayload
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class BookingApiDataSource2(
    private val services: TerbanginApiServices,
) : BookingDataSource2 {
    override suspend fun createBooking(payload: BookingPayload): Response<BookingDataResponse?> {
        return services.createBooking(payload)
    }
}
