package com.arkan.terbangin.data.datasource.seat

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.seat.SeatData

interface SeatDataSource {
    suspend fun getSeat(id: String): Response<List<SeatData>?>
}
