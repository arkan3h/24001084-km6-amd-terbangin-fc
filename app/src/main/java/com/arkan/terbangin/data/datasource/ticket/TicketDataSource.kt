package com.arkan.terbangin.data.datasource.ticket

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.ticket.TicketDataResponse
import com.arkan.terbangin.data.source.network.model.ticket.TicketPayload

interface TicketDataSource {
    suspend fun sendTicket(payload: TicketPayload): Response<TicketDataResponse?>
}
