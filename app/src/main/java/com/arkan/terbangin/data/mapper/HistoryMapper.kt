package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponseData

fun HelperBookingResponseData?.toHistoryModel() =
    History(
        id = this?.id.orEmpty(),
        bookingId = this?.bookingId.orEmpty(),
        userId = this?.booking?.userId.orEmpty(),
        flightId = this?.seat?.flightId.orEmpty(),
        paymentId = this?.booking?.paymentId.orEmpty(),
        bookingStatus = this?.booking?.status.orEmpty(),
        startLoc = this?.seat?.flight?.startAirport?.city.orEmpty(),
        departureAt = this?.seat?.flight?.departureAt.orEmpty(),
        duration = this?.seat?.flight?.duration ?:0,
        endLoc = this?.seat?.flight?.endAirport?.city.orEmpty(),
        arrivalAt = this?.seat?.flight?.arrivalAt.orEmpty(),
        bookingCode = this?.booking?.bookingCode.orEmpty(),
        classes = this?.seat?.airlineClass.orEmpty(),
        totalPayment = this?.booking?.payment?.totalPrice.orEmpty()
    )

fun Collection<HelperBookingResponseData>?.toHistoryList() =
    this?.map {
        it.toHistoryModel()
    } ?: listOf()
