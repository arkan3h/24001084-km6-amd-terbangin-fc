package com.arkan.terbangin.data.datasource.history

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponseData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class HistoryDataSourceImpl(private val service: TerbanginApiServices) : HistoryDataSource {
    override suspend fun getHistoryData(
        id: String,
        status: String,
    ): Response<List<HelperBookingResponseData>?> {
        return service.getHelperBookingById(id, status)
    }

    override suspend fun getDetailHistory(id: String): Response<List<HelperBookingResponseData>?> {
        return service.getDetailHistory(id)
    }
}
