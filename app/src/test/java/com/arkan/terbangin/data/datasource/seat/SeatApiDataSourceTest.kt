package com.arkan.terbangin.data.datasource.seat

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.seat.SeatData
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

class SeatApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: SeatDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = SeatApiDataSource(services)
    }

    @Test
    fun getSeat() {
        runTest {
            val mockResponse = mockk<Response<List<SeatData>?>>(relaxed = true)

            coEvery {
                services.getSeat(any())
            } returns mockResponse

            val actualResult = dataSource.getSeat("id")

            coVerify {
                services.getSeat("id")
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
