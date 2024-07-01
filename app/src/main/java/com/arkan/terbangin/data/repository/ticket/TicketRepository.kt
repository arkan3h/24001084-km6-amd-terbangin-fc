package com.arkan.terbangin.data.repository.ticket

import com.arkan.terbangin.data.datasource.ticket.TicketDataSource
import com.arkan.terbangin.data.mapper.toPayload
import com.arkan.terbangin.data.mapper.toTicket
import com.arkan.terbangin.data.model.Ticket
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun sendTicket(form: Ticket): Flow<ResultWrapper<Ticket>>
}

class TicketRepositoryImpl(
    private val dataSource: TicketDataSource,
) : TicketRepository {
    override fun sendTicket(form: Ticket): Flow<ResultWrapper<Ticket>> {
        return proceedFlow {
            dataSource.sendTicket(form.toPayload()).toTicket()
        }
    }
}
