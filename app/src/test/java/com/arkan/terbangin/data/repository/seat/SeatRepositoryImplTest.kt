package com.arkan.terbangin.data.repository.seat

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.seat.SeatDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.seat.SeatData
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

class SeatRepositoryImplTest {
    @MockK
    lateinit var dataSource: SeatDataSource

    private lateinit var seatRepository: SeatRepository

    private val seat1 =
        SeatData(
            id = "1",
            isAvailable = true,
            seatNumber = 1,
            flightId = "1",
            deletedAt = "1",
            airlineClass = "1",
            createdAt = "1",
            updatedAt = "1",
        )
    private val seat2 =
        SeatData(
            id = "2",
            isAvailable = true,
            seatNumber = 2,
            flightId = "2",
            deletedAt = "2",
            airlineClass = "2",
            createdAt = "2",
            updatedAt = "2",
        )
    private val mockSeatList = listOf(seat1, seat2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        seatRepository = SeatRepositoryImpl(dataSource)
    }

    @Test
    fun getSeatLoading() {
        val mockResponse = mockk<Response<List<SeatData>?>>(relaxed = true)
        every { mockResponse.data } returns mockSeatList

        runTest {
            coEvery { dataSource.getSeat(any()) } returns mockResponse

            seatRepository.getSeat("123", "Economy").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    dataSource.getSeat("123")
                }
            }
        }
    }

    @Test
    fun getSeatSuccess() {
        val mockResponse = mockk<Response<List<SeatData>?>>(relaxed = true)
        every { mockResponse.data } returns mockSeatList

        runTest {
            coEvery { dataSource.getSeat(any()) } returns mockResponse

            seatRepository.getSeat("456", "1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    dataSource.getSeat("456")
                }
            }
        }
    }

    @Test
    fun getSeatError() {
        runTest {
            coEvery { dataSource.getSeat(any()) } throws IllegalStateException("Mock Error")

            seatRepository.getSeat("789", "Economy").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    dataSource.getSeat("789")
                }
            }
        }
    }

    @Test
    fun getSeatEmpty() {
        val mockResponse = mockk<Response<List<SeatData>?>>(relaxed = true)
        every { mockResponse.data } returns emptyList()

        runTest {
            coEvery { dataSource.getSeat(any()) } returns mockResponse

            seatRepository.getSeat("101", "First").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    dataSource.getSeat("101")
                }
            }
        }
    }
}
