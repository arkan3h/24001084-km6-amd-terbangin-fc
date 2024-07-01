package com.arkan.terbangin.data.datasource.ticket

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.ticket.TicketDataResponse
import com.arkan.terbangin.data.source.network.model.ticket.TicketPayload
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class TicketApiDataSource(
    private val services: TerbanginApiServices,
) : TicketDataSource {
    override suspend fun sendTicket(payload: TicketPayload): Response<TicketDataResponse?> {
        return services.sendTicket(payload)
    }
}
