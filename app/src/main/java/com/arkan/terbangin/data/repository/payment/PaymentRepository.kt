package com.arkan.terbangin.data.repository.payment

import com.arkan.terbangin.data.datasource.payment.PaymentDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.payment.PaymentData
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun createPayment(totalPrice: Int): Flow<ResultWrapper<Response<PaymentData?>>>
}

class PaymentRepositoryImpl(
    private val dataSource: PaymentDataSource,
) : PaymentRepository {
    override fun createPayment(totalPrice: Int): Flow<ResultWrapper<Response<PaymentData?>>> {
        return proceedFlow {
            dataSource.createPayment(totalPrice)
        }
    }
}
