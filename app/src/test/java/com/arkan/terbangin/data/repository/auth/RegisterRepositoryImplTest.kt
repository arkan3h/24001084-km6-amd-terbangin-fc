package com.arkan.terbangin.data.repository.auth

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterData
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterUser
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

class RegisterRepositoryImplTest {
    @MockK
    lateinit var dataSource: RegisterDataSource

    private lateinit var repository: RegisterRepository

    private val mockRegisterUser =
        RegisterUser(
            createdAt = "1",
            deletedAt = "1",
            email = "1",
            fullName = "1",
            id = "1",
            phoneNumber = "1",
            picture = "1",
            updatedAt = "1",
        )
    private val mockRegisterData =
        RegisterData(
            token = "1",
            user = mockRegisterUser,
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = RegisterRepositoryImpl(dataSource)
    }

    @Test
    fun doRegisterLoading() {
        val mockResponse = mockk<Response<RegisterData?>>(relaxed = true)
        every { mockResponse.data } returns mockRegisterData

        runTest {
            coEvery {
                dataSource.doRegister(any(), any(), any(), any())
            } returns mockResponse

            repository.doRegister("John Doe", "john.doe@example.com", "1234567890", "password").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegisterSuccess() {
        val mockResponse = mockk<Response<RegisterData?>>(relaxed = true)
        every { mockResponse.data } returns mockRegisterData

        runTest {
            coEvery {
                dataSource.doRegister(any(), any(), any(), any())
            } returns mockResponse

            repository.doRegister("John Doe", "john.doe@example.com", "1234567890", "password").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doRegister(any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegisterError() {
        runTest {
            coEvery {
                dataSource.doRegister(any(), any(), any(), any())
            } throws IllegalStateException("Mock Error")

            repository.doRegister("John Doe", "john.doe@example.com", "1234567890", "password").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any(), any()) }
            }
        }
    }
}
