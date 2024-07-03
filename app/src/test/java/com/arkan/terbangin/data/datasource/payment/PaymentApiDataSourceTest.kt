package com.arkan.terbangin.data.datasource.payment

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.payment.PaymentData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PaymentApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: PaymentDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = PaymentApiDataSource(services)
    }

    @Test
    fun createPayment() {
        runTest {
            val totalPrice = 100

            val mockResponse = mockk<Response<PaymentData?>>(relaxed = true)

            coEvery {
                services.createPayment(totalPrice)
            } returns mockResponse

            val actualResult = dataSource.createPayment(totalPrice)

            coVerify {
                services.createPayment(totalPrice)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
