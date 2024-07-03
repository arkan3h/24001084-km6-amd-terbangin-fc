package com.arkan.terbangin.data.repository.auth

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.auth.login.LoginDataSource
import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.login.LoginData
import com.arkan.terbangin.data.source.network.model.auth.login.LoginUser
import com.arkan.terbangin.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
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

class LoginRepositoryImplTest {
    @MockK
    lateinit var dataSource: LoginDataSource

    @MockK
    lateinit var preferenceDataSource: PreferenceDataSource

    private lateinit var repo: LoginRepository

    private val loginUser =
        LoginUser(
            createdAt = "1",
            email = "1",
            fullName = "1",
            id = "1",
            password = "1",
            phoneNumber = "1",
            picture = "1",
            updatedAt = "1",
        )
    private val loginData =
        LoginData(
            token = "sample_token",
            user = loginUser,
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = LoginRepositoryImpl(dataSource, preferenceDataSource)
    }

    @Test
    fun doLoginLoading() {
        val mockResponse = mockk<Response<LoginData?>>(relaxed = true)
        every {
            mockResponse.data
        } returns loginData

        runTest {
            coEvery {
                dataSource.doLogin(any(), any())
            } returns mockResponse
            repo.doLogin("test@example.com", "password").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    dataSource.doLogin(any(), any())
                }
            }
        }
    }

    @Test
    fun doLoginSuccess() {
        val mockResponse = mockk<Response<LoginData?>>(relaxed = true)
        every {
            mockResponse.data
        } returns loginData

        runTest {
            coEvery {
                dataSource.doLogin(any(), any())
            } returns mockResponse
            coJustRun {
                preferenceDataSource.saveToken(any())
                preferenceDataSource.saveIDUser(any())
            }
            repo.doLogin("test@example.com", "password").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    dataSource.doLogin(any(), any())
                    preferenceDataSource.saveToken("sample_token")
                    preferenceDataSource.saveIDUser("1")
                }
            }
        }
    }

    @Test
    fun doLoginError() {
        runTest {
            coEvery {
                dataSource.doLogin(any(), any())
            } throws IllegalStateException("Mock Error")
            repo.doLogin("test@example.com", "password").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    dataSource.doLogin(any(), any())
                }
            }
        }
    }
}
