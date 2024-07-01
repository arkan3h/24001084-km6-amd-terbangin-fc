package com.arkan.terbangin.data.repository.payment

import com.arkan.terbangin.data.datasource.booking.BookingDataSource
import com.arkan.terbangin.data.datasource.booking.HelperBookingDataSource
import com.arkan.terbangin.data.datasource.payment.PaymentDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.BookingPayload
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingPayload
import com.arkan.terbangin.data.source.network.model.payment.PaymentData
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun createPayment(
        totalPrice: Int,
        status: String,
        passengerId: List<String>,
        seatId: List<String>,
        seatReturnId: List<String>,
    ): Flow<ResultWrapper<Response<PaymentData?>>>
}

class PaymentRepositoryImpl(
    private val dataSource: PaymentDataSource,
    private val dataSourceBooking: BookingDataSource,
    private val dataSourceHelperBooking: HelperBookingDataSource,
    private val pref: UserPreference,
) : PaymentRepository {
    override fun createPayment(
        totalPrice: Int,
        status: String,
        passengerId: List<String>,
        seatId: List<String>,
        seatReturnId: List<String>,
    ): Flow<ResultWrapper<Response<PaymentData?>>> {
        return proceedFlow {
            val payment = dataSource.createPayment(totalPrice)
            payment.data?.id.let { paymentId ->
                val booking = dataSourceBooking.createBooking(BookingPayload(paymentId, status, pref.getUserID()))
                booking.data?.id.let { bookingId ->
                    passengerId.zip(seatId).forEach { (passenger, seat) ->
                        dataSourceHelperBooking.createHelperBooking(HelperBookingPayload(bookingId, passenger, seat))
                    }
                    if (status == "Return") {
                        passengerId.zip(seatReturnId).forEach { (passenger, seat) ->
                            dataSourceHelperBooking.createHelperBooking(HelperBookingPayload(bookingId, passenger, seat))
                        }
                    }
                }
                booking
            }
            payment
        }
    }
}
