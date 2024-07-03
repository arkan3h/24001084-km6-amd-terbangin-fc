package com.arkan.terbangin.data.repository.history

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.history.HistoryDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.history.Airline
import com.arkan.terbangin.data.source.network.model.history.Booking
import com.arkan.terbangin.data.source.network.model.history.EndAirport
import com.arkan.terbangin.data.source.network.model.history.Flight
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponseData
import com.arkan.terbangin.data.source.network.model.history.Passanger
import com.arkan.terbangin.data.source.network.model.history.Payment
import com.arkan.terbangin.data.source.network.model.history.Seat
import com.arkan.terbangin.data.source.network.model.history.StartAirport
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

class HistoryRepositoryImplTest {
    @MockK
    lateinit var ds: HistoryDataSource

    private lateinit var repo: HistoryRepository

    private val mockPayment =
        Payment(
            id = "1",
            userId = "1",
            deletedAt = "1",
            updatedAt = "1",
            createdAt = "1",
            method = "1",
            status = "1",
            snapLink = "1",
            snapToken = "1",
            totalPrice = "1",
        )
    private val mockBooking =
        Booking(
            id = "1",
            userId = "1",
            status = "1",
            createdAt = "1",
            updatedAt = "1",
            deletedAt = "1",
            bookingCode = "1",
            paymentId = "1",
            roundtripFlightId = "1",
            payment = mockPayment,
        )
    private val mockPassenger =
        Passanger(
            id = "1",
            identityId = "1",
            identityIdExpired = "1",
            userId = "1",
            deletedAt = "1",
            createdAt = "1",
            updatedAt = "1",
            issuingCountry = "1",
            title = "1",
            birthDate = "1",
            type = "1",
            fullName = "1",
            familyName = "1",
            nationality = "1",
        )
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
    private val flight =
        Flight(
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
    private val mockSeat =
        Seat(
            id = "1",
            isAvailable = true,
            seatNumber = 1,
            deletedAt = "1",
            createdAt = "1",
            updatedAt = "1",
            flightId = "1",
            airlineClass = "1",
            flight = flight,
        )
    private val history1 =
        HelperBookingResponseData(
            id = "1",
            createdAt = "1",
            updatedAt = "1",
            deletedAt = "1",
            bookingId = "1",
            passangerId = "1",
            seatId = "1",
            seat = mockSeat,
            booking = mockBooking,
            passanger = mockPassenger,
        )
    private val history2 =
        HelperBookingResponseData(
            id = "2",
            createdAt = "2",
            updatedAt = "2",
            deletedAt = "2",
            bookingId = "2",
            passangerId = "2",
            seatId = "2",
            seat = mockSeat,
            booking = mockBooking,
            passanger = mockPassenger,
        )
    private val mockListHistory = listOf(history1, history2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = HistoryRepositoryImpl(ds)
    }

    @Test
    fun getHistoryDataLoading() {
        val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>()
        every { mockResponse.data } returns mockListHistory

        runTest {
            coEvery {
                ds.getHistoryData(any(), any())
            } returns mockResponse

            repo.getHistoryData("", "", "").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getHistoryData(any(), any())
                }
            }
        }
    }

    @Test
    fun getHistoryDataSuccess() {
        val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>()
        every { mockResponse.data } returns mockListHistory

        runTest {
            coEvery {
                ds.getHistoryData(any(), any())
            } returns mockResponse

            repo.getHistoryData("", "", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getHistoryData(any(), any())
                }
            }
        }
    }

    @Test
    fun getHistoryDataError() {
        runTest {
            coEvery {
                ds.getHistoryData(any(), any())
            } throws IllegalStateException("Mock Error")

            repo.getHistoryData("", "", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getHistoryData(any(), any())
                }
            }
        }
    }

    @Test
    fun getHistoryDataEmpty() {
        val mockListHistory = listOf<HelperBookingResponseData>()
        val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>()
        every { mockResponse.data } returns mockListHistory

        runTest {
            coEvery {
                ds.getHistoryData(any(), any())
            } returns mockResponse

            repo.getHistoryData("", "", "").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getHistoryData(any(), any())
                }
            }
        }
    }

    @Test
    fun getDetailHistoryDataLoading() {
        val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>()
        every { mockResponse.data } returns mockListHistory

        runTest {
            coEvery {
                ds.getDetailHistory(any())
            } returns mockResponse

            repo.getDetailHistoryData("").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getDetailHistory(any())
                }
            }
        }
    }

    @Test
    fun getDetailHistoryDataSuccess() {
        val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>()
        every { mockResponse.data } returns mockListHistory

        runTest {
            coEvery {
                ds.getDetailHistory(any())
            } returns mockResponse

            repo.getDetailHistoryData("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getDetailHistory(any())
                }
            }
        }
    }

    @Test
    fun getDetailHistoryDataError() {
        runTest {
            coEvery {
                ds.getDetailHistory(any())
            } throws IllegalStateException("Mock Error")

            repo.getDetailHistoryData("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getDetailHistory(any())
                }
            }
        }
    }

    @Test
    fun getDetailHistoryDataEmpty() {
        val mockListHistory = listOf<HelperBookingResponseData>()
        val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>()
        every { mockResponse.data } returns mockListHistory

        runTest {
            coEvery {
                ds.getDetailHistory(any())
            } returns mockResponse

            repo.getDetailHistoryData("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getDetailHistory(any())
                }
            }
        }
    }
}
