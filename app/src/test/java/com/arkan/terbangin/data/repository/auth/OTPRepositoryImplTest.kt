package com.arkan.terbangin.data.repository.auth

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.auth.otp.OTPDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPData
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPData
import com.arkan.terbangin.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OTPRepositoryImplTest {
    @MockK
    lateinit var dataSource: OTPDataSource

    private lateinit var repository: OTPRepository

    private val mockRequestOTPData =
        RequestOTPData(
            code = "1",
            createdAt = "1",
            deletedAt = "1",
            email = "1",
            expire = "1",
            id = "1",
            isUsed = false,
            updatedAt = "1",
        )
    private val mockVerifyOTPData =
        VerifyOTPData(
            code = "1",
            createdAt = "1",
            deletedAt = "1",
            email = "1",
            expire = "1",
            id = "1",
            isUsed = false,
            updatedAt = "1",
            phoneNumber = "1",
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = OTPRepositoryImpl(dataSource)
    }

    @Test
    fun requestOTPLoading() {
        val mockResponse = mockk<Response<RequestOTPData?>>(relaxed = true)
        every { mockResponse.data } returns mockRequestOTPData

        runTest {
            coEvery { dataSource.requestOTP(any()) } returns mockResponse

            repository.requestOTP("test@example.com").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.requestOTP(any()) }
            }
        }
    }

    @Test
    fun requestOTPSuccess() {
        val mockResponse = mockk<Response<RequestOTPData?>>(relaxed = true)
        every { mockResponse.data } returns mockRequestOTPData

        runTest {
            coEvery { dataSource.requestOTP(any()) } returns mockResponse

            repository.requestOTP("test@example.com").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.requestOTP(any()) }
            }
        }
    }

    @Test
    fun requestOTPError() {
        runTest {
            coEvery { dataSource.requestOTP(any()) } throws IllegalStateException("Mock Error")

            repository.requestOTP("test@example.com").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.requestOTP(any()) }
            }
        }
    }

    @Test
    fun verifyOTPLoading() {
        val mockResponse = mockk<Response<VerifyOTPData?>>(relaxed = true)
        every { mockResponse.data } returns mockVerifyOTPData

        runTest {
            coEvery { dataSource.verifyOTP(any(), any()) } returns mockResponse

            repository.verifyOTP("test@example.com", "123456").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.verifyOTP(any(), any()) }
            }
        }
    }

    @Test
    fun verifyOTPSuccess() {
        val mockResponse = mockk<Response<VerifyOTPData?>>(relaxed = true)
        every { mockResponse.data } returns mockVerifyOTPData

        runTest {
            coEvery { dataSource.verifyOTP(any(), any()) } returns mockResponse

            repository.verifyOTP("test@example.com", "123456").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.verifyOTP(any(), any()) }
            }
        }
    }

    @Test
    fun verifyOTPError() {
        runTest {
            coEvery { dataSource.verifyOTP(any(), any()) } throws IllegalStateException("Mock Error")

            repository.verifyOTP("test@example.com", "123456").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.verifyOTP(any(), any()) }
            }
        }
    }
}
