package com.arkan.terbangin.data.datasource.booking

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingPayload
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

class HelperBookingApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: HelperBookingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = HelperBookingApiDataSource(services)
    }

    @Test
    fun createHelperBooking() {
        runTest {
            val mockPayload = mockk<HelperBookingPayload>()

            val mockResponse = mockk<Response<HelperBookingDataResponse?>>(relaxed = true)

            coEvery {
                services.createHelperBooking(any())
            } returns mockResponse

            val actualResult = dataSource.createHelperBooking(mockPayload)

            coVerify {
                services.createHelperBooking(mockPayload)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
