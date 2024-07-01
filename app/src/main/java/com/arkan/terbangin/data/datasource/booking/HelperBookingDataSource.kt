package com.arkan.terbangin.data.datasource.booking

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingPayload

interface HelperBookingDataSource {
    suspend fun createHelperBooking(payload: HelperBookingPayload): Response<HelperBookingDataResponse?>
}
