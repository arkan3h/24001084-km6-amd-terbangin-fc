package com.arkan.terbangin.data.repository.payment

import com.arkan.terbangin.data.datasource.booking2.BookingDataSource2
import com.arkan.terbangin.data.datasource.booking2.HelperBookingDataSource2
import com.arkan.terbangin.data.datasource.payment.PaymentDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking2.BookingPayload
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingPayload
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
    ): Flow<ResultWrapper<Response<PaymentData?>>>
}

class PaymentRepositoryImpl(
    private val dataSource: PaymentDataSource,
    private val dataSourceBooking: BookingDataSource2,
    private val dataSourceHelperBooking: HelperBookingDataSource2,
    private val pref: UserPreference,
) : PaymentRepository {
    override fun createPayment(
        totalPrice: Int,
        status: String,
        passengerId: List<String>,
        seatId: List<String>,
    ): Flow<ResultWrapper<Response<PaymentData?>>> {
        return proceedFlow {
            val payment = dataSource.createPayment(totalPrice)
            payment.data?.id.let {
                val booking = dataSourceBooking.createBooking(BookingPayload(it, status, pref.getUserID()))
                booking.data?.id.let {
                    passengerId.zip(seatId).forEach { (passenger, seat) ->
                        dataSourceHelperBooking.createHelperBooking(HelperBookingPayload(it, passenger, seat))
                    }
                }
                booking
            }
            payment
        }
    }
}
