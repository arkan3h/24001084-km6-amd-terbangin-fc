package com.arkan.terbangin.data.datasource.booking

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.BookingPayload

interface BookingDataSource {
    suspend fun createBooking(payload: BookingPayload): Response<BookingDataResponse?>
}
