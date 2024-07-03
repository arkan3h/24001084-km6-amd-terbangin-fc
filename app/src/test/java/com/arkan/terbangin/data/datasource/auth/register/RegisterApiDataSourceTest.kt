package com.arkan.terbangin.data.datasource.auth.register

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterData
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

class RegisterApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: RegisterDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = RegisterApiDataSource(services)
    }

    @Test
    fun doRegister() {
        runTest {
            val mockResponse = mockk<Response<RegisterData?>>(relaxed = true)

            coEvery {
                services.doRegister(any(), any(), any(), any())
            } returns mockResponse

            val fullName = mockk<RequestBody>()
            val email = mockk<RequestBody>()
            val phoneNumber = mockk<RequestBody>()
            val password = mockk<RequestBody>()
            val actualResult =
                dataSource.doRegister(
                    fullName,
                    email,
                    phoneNumber,
                    password,
                )

            coVerify {
                services.doRegister(
                    fullName,
                    email,
                    phoneNumber,
                    password,
                )
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
