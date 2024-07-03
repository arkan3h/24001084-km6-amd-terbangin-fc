package com.arkan.terbangin.data.datasource.booking

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.BookingPayload
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

class BookingApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: BookingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = BookingApiDataSource(services)
    }

    @Test
    fun createBooking() {
        runTest {
            val mockPayload = mockk<BookingPayload>()

            val mockResponse = mockk<Response<BookingDataResponse?>>(relaxed = true)

            coEvery {
                services.createBooking(any())
            } returns mockResponse

            val actualResult = dataSource.createBooking(mockPayload)

            coVerify {
                services.createBooking(mockPayload)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
