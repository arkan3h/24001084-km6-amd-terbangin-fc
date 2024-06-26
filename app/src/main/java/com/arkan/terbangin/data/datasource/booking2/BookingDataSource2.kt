package com.arkan.terbangin.data.datasource.booking2

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking2.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking2.BookingPayload

interface BookingDataSource2 {
    suspend fun createBooking(payload: BookingPayload): Response<BookingDataResponse?>
}
