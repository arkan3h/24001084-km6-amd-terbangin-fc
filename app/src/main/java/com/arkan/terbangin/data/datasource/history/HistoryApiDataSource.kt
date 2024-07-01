package com.arkan.terbangin.data.datasource.history

import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class HistoryDataSourceImpl(private val service: TerbanginApiServices) : HistoryDataSource {
    override suspend fun getHistoryData(
        id: String,
        status: String,
    ): HelperBookingResponse {
        return service.getHelperBookingById(id, status)
    }
}
