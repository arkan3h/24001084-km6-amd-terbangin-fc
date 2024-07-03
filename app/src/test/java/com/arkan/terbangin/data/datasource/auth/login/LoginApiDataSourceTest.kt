package com.arkan.terbangin.data.datasource.auth.login

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.login.LoginData
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

class LoginApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: LoginDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = LoginApiDataSource(services)
    }

    @Test
    fun doLogin() {
        runTest {
            val mockResponse = mockk<Response<LoginData?>>(relaxed = true)

            coEvery {
                services.doLogin(any(), any())
            } returns mockResponse

            val email = mockk<RequestBody>()
            val password = mockk<RequestBody>()
            val actualResult = dataSource.doLogin(email, password)

            coVerify {
                services.doLogin(email, password)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
