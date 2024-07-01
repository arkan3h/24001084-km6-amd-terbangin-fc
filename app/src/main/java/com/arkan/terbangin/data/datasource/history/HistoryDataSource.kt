package com.arkan.terbangin.data.datasource.history

import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponse

interface HistoryDataSource {
    suspend fun getHistoryData(
        id: String,
        status: String,
    ): HelperBookingResponse
}
