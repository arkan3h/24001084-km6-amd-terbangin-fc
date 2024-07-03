package com.arkan.terbangin.data.datasource.ticket

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.ticket.TicketDataResponse
import com.arkan.terbangin.data.source.network.model.ticket.TicketPayload
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

class TicketApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: TicketDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = TicketApiDataSource(services)
    }

    @Test
    fun sendTicket() {
        runTest {
            val mockPayload = mockk<TicketPayload>()

            val mockResponse = mockk<Response<TicketDataResponse?>>(relaxed = true)

            coEvery {
                services.sendTicket(mockPayload)
            } returns mockResponse

            val actualResult = dataSource.sendTicket(mockPayload)

            coVerify {
                services.sendTicket(mockPayload)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
