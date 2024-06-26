package com.arkan.terbangin.data.datasource.booking2

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingPayload

interface HelperBookingDataSource2 {
    suspend fun createHelperBooking(payload: HelperBookingPayload): Response<HelperBookingDataResponse?>
}
