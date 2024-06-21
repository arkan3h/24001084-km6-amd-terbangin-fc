package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Seat
import com.arkan.terbangin.data.source.network.model.seat.SeatData

fun SeatData?.toSeat() =
    Seat(
        id = this?.id.orEmpty(),
        seatNumber = this?.seatNumber ?: 0,
        flightId = this?.flightId.orEmpty(),
        airlineClass = this?.airlineClass.orEmpty(),
        isAvailable = this?.isAvailable ?: false,
    )

fun Collection<SeatData>?.toSeat() =
    this?.map {
        it.toSeat()
    } ?: listOf()
