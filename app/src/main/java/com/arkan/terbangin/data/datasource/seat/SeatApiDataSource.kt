package com.arkan.terbangin.data.datasource.seat

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.seat.SeatData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class SeatApiDataSource(
    private val services: TerbanginApiServices,
) : SeatDataSource {
    override suspend fun getSeat(id: String): Response<List<SeatData>?> {
        return services.getSeat(id)
    }
}
