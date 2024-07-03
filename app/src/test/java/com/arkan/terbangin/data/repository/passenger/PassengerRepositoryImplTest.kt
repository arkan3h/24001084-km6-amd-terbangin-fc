package com.arkan.terbangin.data.repository.passenger

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.passenger.PassengerDataSource
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponseData
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

class PassengerRepositoryImplTest {
    @MockK
    lateinit var ds: PassengerDataSource

    private lateinit var repo: PassengerRepository

    private val passengerData =
        PassengerResponseData(
            id = "1",
            identityId = "1",
            userId = "1",
            issuingCountry = "1",
            title = "1",
            createdAt = "1",
            updatedAt = "1",
            deletedAt = "1",
            type = "1",
            fullName = "1",
            birthDate = "1",
            familyName = "1",
            nationality = "1",
        )
    private val passengerBioData =
        PassengerBioData(
            id = "1",
            userId = "1",
            type = "1",
            fullName = "1",
            familyName = "1",
            nationality = "1",
            title = "1",
            identityId = "1",
            issuingCountry = "1",
            birthDay = "1",
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = PassengerRepositoryImpl(ds)
    }

    @Test
    fun createPassengerLoading() {
        val mockResponse = mockk<Response<PassengerResponseData?>>(relaxed = true)
        every { mockResponse.data } returns passengerData

        runTest {
            coEvery {
                ds.createPassenger(any())
            } returns mockResponse

            repo.createPassenger(passengerBioData).map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.createPassenger(any())
                }
            }
        }
    }

    @Test
    fun createPassengerSuccess() {
        val mockResponse = mockk<Response<PassengerResponseData?>>(relaxed = true)
        every { mockResponse.data } returns passengerData

        runTest {
            coEvery {
                ds.createPassenger(any())
            } returns mockResponse

            repo.createPassenger(passengerBioData).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.createPassenger(any())
                }
            }
        }
    }

    @Test
    fun createPassengerError() {
        runTest {
            coEvery {
                ds.createPassenger(any())
            } throws IllegalStateException("Mock Error")

            repo.createPassenger(passengerBioData).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.createPassenger(any())
                }
            }
        }
    }
}
