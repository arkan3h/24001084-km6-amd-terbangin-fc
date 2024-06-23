package com.arkan.terbangin.data.datasource.payment

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.payment.PaymentData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class PaymentApiDataSource(
    private val services: TerbanginApiServices,
) : PaymentDataSource {
    override suspend fun createPayment(totalPrice: Int): Response<PaymentData?> {
        return services.createPayment(totalPrice)
    }
}
