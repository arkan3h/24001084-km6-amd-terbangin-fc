package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Booking2
import com.arkan.terbangin.data.model.HelperBooking2
import com.arkan.terbangin.data.source.network.model.booking2.BookingPayload
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingPayload

fun Booking2.toPayload() =
    BookingPayload(
        userId = this.userId,
        status = this.status,
        paymentId = this.paymentId,
    )

fun HelperBooking2.toPayload() =
    HelperBookingPayload(
        seatId = this.seatId,
        bookingId = this.bookingId,
        passangerId = this.passengerId,
    )
