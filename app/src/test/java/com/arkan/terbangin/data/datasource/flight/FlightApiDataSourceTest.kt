package com.arkan.terbangin.data.datasource.flight

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.flight.FlightDataResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FlightApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: FlightDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FlightApiDataSource(services)
    }

    @Test
    fun getAllFlight() {
        runTest {
            val mockResponse = mockk<Response<List<FlightDataResponse>?>>(relaxed = true)

            coEvery {
                services.getAllFlight(any(), any(), any(), any(), any(), any(), any())
            } returns mockResponse

            val actualResult =
                dataSource.getAllFlight(
                    "start",
                    "end",
                    "key",
                    "value",
                    "filter",
                    "order",
                    "seatType",
                )

            coVerify {
                services.getAllFlight(
                    "start",
                    "end",
                    "key",
                    "value",
                    "filter",
                    "order",
                    "seatType",
                )
            }

            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getFlightContinent() {
        runTest {
            val mockResponse = mockk<Response<List<FlightDataResponse>?>>(relaxed = true)

            coEvery {
                services.getFlightContinent(any(), any())
            } returns mockResponse

            val actualResult = dataSource.getFlightContinent("date", "continent")

            coVerify {
                services.getFlightContinent("date", "continent")
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
