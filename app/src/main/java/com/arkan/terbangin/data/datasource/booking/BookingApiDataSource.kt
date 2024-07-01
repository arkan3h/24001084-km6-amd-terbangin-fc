package com.arkan.terbangin.data.datasource.booking

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.BookingPayload
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class BookingApiDataSource(
    private val services: TerbanginApiServices,
) : BookingDataSource {
    override suspend fun createBooking(payload: BookingPayload): Response<BookingDataResponse?> {
        return services.createBooking(payload)
    }
}
