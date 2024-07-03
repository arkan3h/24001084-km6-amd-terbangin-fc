package com.arkan.terbangin.data.repository.auth

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordDataSource
import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
import com.arkan.terbangin.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ResetPasswordRepositoryImplTest {
    @MockK
    lateinit var dataSource: ResetPasswordDataSource

    private lateinit var repository: ResetPasswordRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ResetPasswordRepositoryImpl(dataSource)
    }

    @Test
    fun doResetPasswordLoading() {
        val mockResponse = mockk<ResetPasswordResponse>(relaxed = true)

        runTest {
            coEvery {
                dataSource.doResetPassword(any())
            } returns mockResponse

            repository.doResetPassword("john.doe@example.com").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.doResetPassword(any()) }
            }
        }
    }

    @Test
    fun doResetPasswordSuccess() {
        val mockResponse = mockk<ResetPasswordResponse>()

        runTest {
            coEvery {
                dataSource.doResetPassword(any())
            } returns mockResponse

            repository.doResetPassword("john.doe@example.com").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doResetPassword(any()) }
            }
        }
    }

    @Test
    fun doResetPasswordError() {
        runTest {
            coEvery {
                dataSource.doResetPassword(any())
            } throws IllegalStateException("Mock Error")

            repository.doResetPassword("john.doe@example.com").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.doResetPassword(any()) }
            }
        }
    }
}
