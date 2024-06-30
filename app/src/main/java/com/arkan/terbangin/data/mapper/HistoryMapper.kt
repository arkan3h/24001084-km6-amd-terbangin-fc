package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.DetailHistory
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.data.model.Passenger
import com.arkan.terbangin.data.model.PassengerHistory
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponseData

fun HelperBookingResponseData?.toHistoryModel() =
    History(
        id = this?.id.orEmpty(),
        bookingId = this?.bookingId.orEmpty(),
        userId = this?.booking?.userId.orEmpty(),
        flightId = this?.seat?.flightId.orEmpty(),
        paymentId = this?.booking?.paymentId.orEmpty(),
        bookingStatus = this?.booking?.payment?.status.orEmpty(),
        startLoc = this?.seat?.flight?.startAirport?.city.orEmpty(),
        departureAt = this?.seat?.flight?.departureAt.orEmpty(),
        duration = this?.seat?.flight?.duration ?: 0,
        endLoc = this?.seat?.flight?.endAirport?.city.orEmpty(),
        arrivalAt = this?.seat?.flight?.arrivalAt.orEmpty(),
        bookingCode = this?.booking?.bookingCode.orEmpty(),
        classes = this?.seat?.airlineClass.orEmpty(),
        totalPayment = this?.booking?.payment?.totalPrice.orEmpty(),
        monthHeader = this?.seat?.flight?.createdAt.orEmpty(),
    )

fun HelperBookingResponseData?.toDetailHistoryModel() =
    DetailHistory(
        id = this?.id.orEmpty(),
        bookingStatus = this?.booking?.payment?.status.orEmpty(),
        bookingCode = this?.booking?.bookingCode.orEmpty(),
        departureAt = this?.seat?.flight?.departureAt.orEmpty(),
        arrivalAt = this?.seat?.flight?.arrivalAt.orEmpty(),
        airportStart = this?.seat?.flight?.startAirport?.name.orEmpty(),
        terminalStart = this?.seat?.flight?.startAirport?.terminal.orEmpty(),
        airportEnd = this?.seat?.flight?.endAirport?.name.orEmpty(),
        terminalEnd = this?.seat?.flight?.endAirport?.terminal.orEmpty(),
        aircraftName = this?.seat?.flight?.airline?.name.orEmpty(),
        classes = this?.seat?.airlineClass.orEmpty(),
        aircraftType = this?.seat?.flight?.airline?.aircraftType.orEmpty(),
        totalPrice = this?.booking?.payment?.totalPrice.orEmpty(),
        passengerId = this?.passangerId.
    )

fun HelperBookingResponseData?.toPassengerHistoryModel() =
    PassengerHistory(
        passengerId = this?.passangerId.orEmpty()
    )

fun Collection<HelperBookingResponseData>?.toHistoryList() =
    this?.map {
        it.toHistoryModel()
    } ?: listOf()

fun Collection<HelperBookingResponseData>?.toDetailHistory() =
    this?.map {
        it.toDetailHistoryModel()
    } ?: listOf()

fun Collection<HelperBookingResponseData>?.toPassengerHistory() =
    this?.map {
        it.toPassengerHistoryModel()
    } ?: listOf()