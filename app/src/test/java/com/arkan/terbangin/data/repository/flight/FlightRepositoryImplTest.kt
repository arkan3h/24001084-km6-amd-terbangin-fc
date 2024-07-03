package com.arkan.terbangin.data.repository.flight

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.flight.FlightDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.flight.Airline
import com.arkan.terbangin.data.source.network.model.flight.EndAirport
import com.arkan.terbangin.data.source.network.model.flight.FlightDataResponse
import com.arkan.terbangin.data.source.network.model.flight.StartAirport
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

class FlightRepositoryImplTest {
    @MockK
    lateinit var ds: FlightDataSource

    private lateinit var repo: FlightRepository

    private val mockAirline =
        Airline(
            id = "1",
            iataCode = "1",
            deletedAt = "1",
            updatedAt = "1",
            createdAt = "1",
            name = "1",
            baggage = 1,
            picture = "1",
            aircraftType = "1",
            additionals = "1",
            cabinBaggage = 1,
        )
    private val mockStartAirport =
        StartAirport(
            id = "1",
            iataCode = "1",
            deletedAt = "1",
            updatedAt = "1",
            createdAt = "1",
            name = "1",
            picture = "1",
            city = "1",
            terminal = "1",
            timezone = "1",
            country = "1",
            continent = "1",
        )
    private val mockEndAirport =
        EndAirport(
            id = "2",
            iataCode = "2",
            deletedAt = "2",
            updatedAt = "2",
            createdAt = "2",
            name = "2",
            picture = "2",
            city = "2",
            terminal = "2",
            timezone = "2",
            country = "2",
            continent = "2",
        )
    private val flight1 =
        FlightDataResponse(
            id = "1",
            deletedAt = "1",
            updatedAt = "1",
            createdAt = "1",
            flightCode = "1",
            startAirportId = "1",
            airlineId = "1",
            endAirportId = "1",
            capacityBussines = 1,
            capacityFirstClass = 1,
            duration = 1,
            airline = mockAirline,
            capacityEconomy = 1,
            priceFirstClass = 1,
            arrivalAt = "1",
            priceEconomy = 1,
            startAirport = mockStartAirport,
            endAirport = mockEndAirport,
            priceBussines = 1,
            departureAt = "1",
        )
    private val flight2 =
        FlightDataResponse(
            id = "2",
            deletedAt = "2",
            updatedAt = "2",
            createdAt = "2",
            flightCode = "2",
            startAirportId = "2",
            airlineId = "2",
            endAirportId = "2",
            capacityBussines = 2,
            capacityFirstClass = 2,
            duration = 2,
            airline = mockAirline,
            capacityEconomy = 2,
            priceFirstClass = 2,
            arrivalAt = "2",
            priceEconomy = 2,
            startAirport = mockStartAirport,
            endAirport = mockEndAirport,
            priceBussines = 2,
            departureAt = "2",
        )
    private val mockListFlight = listOf(flight1, flight2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = FlightRepositoryImpl(ds)
    }

    @Test
    fun getAllFlightLoading() {
        val mockResponse = mockk<Response<List<FlightDataResponse>?>>()
        every { mockResponse.data } returns mockListFlight

        runTest {
            coEvery {
                ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
            } returns mockResponse

            repo.getAllFlight("", "", "", "", "", "", "").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
                }
            }
        }
    }

    @Test
    fun getAllFlightSuccess() {
        val mockResponse = mockk<Response<List<FlightDataResponse>?>>()
        every { mockResponse.data } returns mockListFlight

        runTest {
            coEvery {
                ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
            } returns mockResponse

            repo.getAllFlight("", "", "", "", "", "", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
                }
            }
        }
    }

    @Test
    fun getAllFlightError() {
        runTest {
            coEvery {
                ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
            } throws IllegalStateException("Mock Error")

            repo.getAllFlight("", "", "", "", "", "", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
                }
            }
        }
    }

    @Test
    fun getAllFlightEmpty() {
        val mockListFlight = listOf<FlightDataResponse>()
        val mockResponse = mockk<Response<List<FlightDataResponse>?>>()
        every { mockResponse.data } returns mockListFlight

        runTest {
            coEvery {
                ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
            } returns mockResponse

            repo.getAllFlight("", "", "", "", "", "", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getAllFlight(any(), any(), any(), any(), any(), any(), any())
                }
            }
        }
    }

    @Test
    fun getFlightContinentLoading() {
        val mockResponse = mockk<Response<List<FlightDataResponse>?>>()
        every { mockResponse.data } returns mockListFlight

        runTest {
            coEvery {
                ds.getFlightContinent(any(), any())
            } returns mockResponse

            repo.getFlightContinent("", "").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getFlightContinent(any(), any())
                }
            }
        }
    }

    @Test
    fun getFlightContinentSuccess() {
        val mockResponse = mockk<Response<List<FlightDataResponse>?>>()
        every { mockResponse.data } returns mockListFlight

        runTest {
            coEvery {
                ds.getFlightContinent(any(), any())
            } returns mockResponse

            repo.getFlightContinent("", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getFlightContinent(any(), any())
                }
            }
        }
    }

    @Test
    fun getFlightContinentError() {
        runTest {
            coEvery {
                ds.getFlightContinent(any(), any())
            } throws IllegalStateException("Mock Error")

            repo.getFlightContinent("", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getFlightContinent(any(), any())
                }
            }
        }
    }

    @Test
    fun getFlightContinentEmpty() {
        val mockListFlight = listOf<FlightDataResponse>()
        val mockResponse = mockk<Response<List<FlightDataResponse>?>>()
        every { mockResponse.data } returns mockListFlight

        runTest {
            coEvery {
                ds.getFlightContinent(any(), any())
            } returns mockResponse

            repo.getFlightContinent("", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getFlightContinent(any(), any())
                }
            }
        }
    }
}
