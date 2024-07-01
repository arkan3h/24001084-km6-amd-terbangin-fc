package com.arkan.terbangin.data.datasource.history

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponseData

interface HistoryDataSource {
    suspend fun getHistoryData(
        id: String,
        status: String,
    ): Response<List<HelperBookingResponseData>?>

    suspend fun getDetailHistory(id: String): Response<List<HelperBookingResponseData>?>
}
