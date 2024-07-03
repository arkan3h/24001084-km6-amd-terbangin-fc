package com.arkan.terbangin.data.repository.airport

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.airportcity.AirportCityDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.airport.AirportData
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

class AirportCityRepositoryImplTest {
    @MockK
    lateinit var ds: AirportCityDataSource

    private lateinit var repo: AirportCityRepository

    private val airport1 =
        AirportData(
            city = "1",
            continent = "1",
            country = "1",
            createdAt = "1",
            deletedAt = "1",
            iataCode = "1",
            id = "1",
            name = "1",
            picture = "1",
            terminal = "1",
            updatedAt = "1",
        )
    private val airport2 =
        AirportData(
            city = "2",
            continent = "2",
            country = "2",
            createdAt = "2",
            deletedAt = "2",
            iataCode = "2",
            id = "2",
            name = "2",
            picture = "2",
            terminal = "2",
            updatedAt = "2",
        )
    private val mockListAirport = listOf(airport1, airport2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = AirportCityRepositoryImpl(ds)
    }

    @Test
    fun searchAirportCityLoading() {
        val mockResponse = mockk<Response<List<AirportData>?>>(relaxed = true)
        every {
            mockResponse.data
        } returns mockListAirport

        runTest {
            coEvery {
                ds.getAllAirportCity()
            } returns mockResponse
            repo.searchAirportCity("").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getAllAirportCity()
                }
            }
        }
    }

    @Test
    fun searchAirportCitySuccess() {
        val mockResponse = mockk<Response<List<AirportData>?>>(relaxed = true)
        every {
            mockResponse.data
        } returns mockListAirport

        runTest {
            coEvery {
                ds.getAllAirportCity()
            } returns mockResponse
            repo.searchAirportCity("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getAllAirportCity()
                }
            }
        }
    }

    @Test
    fun searchAirportCityError() {
        runTest {
            coEvery {
                ds.getAllAirportCity()
            } throws IllegalStateException("Mock Error")
            repo.searchAirportCity("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getAllAirportCity()
                }
            }
        }
    }

    @Test
    fun searchAirportCityEmpty() {
        val mockListAirport = listOf<AirportData>()
        val mockResponse = mockk<Response<List<AirportData>?>>(relaxed = true)
        every {
            mockResponse.data
        } returns mockListAirport

        runTest {
            coEvery {
                ds.getAllAirportCity()
            } returns mockResponse
            repo.searchAirportCity("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getAllAirportCity()
                }
            }
        }
    }
}
