package com.arkan.terbangin.data.datasource.payment

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.payment.PaymentData

interface PaymentDataSource {
    suspend fun createPayment(totalPrice: Int): Response<PaymentData?>
}
