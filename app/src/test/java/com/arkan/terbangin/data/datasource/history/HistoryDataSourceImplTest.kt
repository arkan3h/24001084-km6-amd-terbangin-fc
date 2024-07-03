package com.arkan.terbangin.data.datasource.history

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponseData
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

class HistoryDataSourceImplTest {
    @MockK
    lateinit var service: TerbanginApiServices

    private lateinit var dataSource: HistoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = HistoryDataSourceImpl(service)
    }

    @Test
    fun getHistoryData() {
        runTest {
            val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>(relaxed = true)

            coEvery {
                service.getHelperBookingById(any(), any())
            } returns mockResponse

            val actualResult = dataSource.getHistoryData("id", "status")

            coVerify {
                service.getHelperBookingById("id", "status")
            }

            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getDetailHistory() {
        runTest {
            val mockResponse = mockk<Response<List<HelperBookingResponseData>?>>(relaxed = true)

            coEvery {
                service.getDetailHistory(any())
            } returns mockResponse

            val actualResult = dataSource.getDetailHistory("id")

            coVerify {
                service.getDetailHistory("id")
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
