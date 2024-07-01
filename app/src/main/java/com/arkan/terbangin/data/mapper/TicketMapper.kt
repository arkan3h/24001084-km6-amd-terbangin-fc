package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.model.Ticket
import com.arkan.terbangin.data.source.network.model.ticket.TicketDataResponse
import com.arkan.terbangin.data.source.network.model.ticket.TicketPayload

fun Response<TicketDataResponse?>.toTicket() =
    Ticket(
        bookingId = this.data?.id.orEmpty(),
        email = this.data?.user?.email.orEmpty(),
    )

fun Ticket.toPayload() =
    TicketPayload(
        bookingId = this.bookingId,
        email = this.email,
    )
