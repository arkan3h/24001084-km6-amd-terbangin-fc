package com.arkan.terbangin.data.datasource.auth.otp

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPData
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.RequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OTPApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: OTPDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = OTPApiDataSource(services)
    }

    @Test
    fun requestOTP() {
        runTest {
            val mockResponse = mockk<Response<RequestOTPData?>>(relaxed = true)

            coEvery {
                services.requestOTP(any())
            } returns mockResponse

            val email = mockk<RequestBody>()
            val actualResult = dataSource.requestOTP(email)

            coVerify {
                services.requestOTP(email)
            }

            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun verifyOTP() {
        runTest {
            val mockResponse = mockk<Response<VerifyOTPData?>>(relaxed = true)

            coEvery {
                services.verifyOTP(any(), any())
            } returns mockResponse

            val email = mockk<RequestBody>()
            val otp = mockk<RequestBody>()
            val actualResult = dataSource.verifyOTP(email, otp)

            coVerify {
                services.verifyOTP(email, otp)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
