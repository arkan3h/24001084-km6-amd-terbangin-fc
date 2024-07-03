package com.arkan.terbangin.data.datasource.auth.resetpassword

import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
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

class ResetPasswordApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: ResetPasswordDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = ResetPasswordApiDataSource(services)
    }

    @Test
    fun doResetPassword() {
        runTest {
            val mockResponse = mockk<ResetPasswordResponse>(relaxed = true)

            coEvery {
                services.doResetPassword(any())
            } returns mockResponse

            val email = mockk<RequestBody>()
            val actualResult = dataSource.doResetPassword(email)

            coVerify {
                services.doResetPassword(email)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
