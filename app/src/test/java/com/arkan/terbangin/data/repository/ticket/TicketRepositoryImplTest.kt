package com.arkan.terbangin.data.repository.ticket

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.ticket.TicketDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.model.Ticket
import com.arkan.terbangin.data.source.network.model.ticket.Payment
import com.arkan.terbangin.data.source.network.model.ticket.TicketDataResponse
import com.arkan.terbangin.data.source.network.model.ticket.User
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

class TicketRepositoryImplTest {
    @MockK
    lateinit var dataSource: TicketDataSource

    private lateinit var ticketRepository: TicketRepository

    private val mockPayment =
        Payment(
            id = "1",
            userId = "1",
            deletedAt = "1",
            totalPrice = "1",
            snapToken = "1",
            snapLink = "1",
            method = "1",
            status = "1",
            createdAt = "1",
            updatedAt = "1",
        )
    private val user =
        User(
            id = "1",
            deletedAt = "1",
            createdAt = "1",
            updatedAt = "1",
            phoneNumber = "1",
            email = "1",
            fullName = "1",
            picture = "1",
        )
    private val mockTicketResponse =
        TicketDataResponse(
            id = "1",
            roundtripFlightId = "1",
            createdAt = "1",
            updatedAt = "1",
            paymentId = "1",
            deletedAt = "1",
            userId = "1",
            bookingCode = "1",
            user = user,
            status = "1",
            payment = mockPayment,
        )
    private val mockTicket =
        Ticket(
            email = "1",
            bookingId = "1",
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ticketRepository = TicketRepositoryImpl(dataSource)
    }

    @Test
    fun sendTicketLoading() {
        val mockResponse = mockk<Response<TicketDataResponse?>>(relaxed = true)
        every { mockResponse.data } returns mockTicketResponse

        runTest {
            coEvery { dataSource.sendTicket(any()) } returns mockResponse

            ticketRepository.sendTicket(mockTicket)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(150)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Loading)
                    coVerify {
                        dataSource.sendTicket(any())
                    }
                }
        }
    }

    @Test
    fun sendTicketSuccess() {
        val mockResponse = mockk<Response<TicketDataResponse?>>(relaxed = true)
        every { mockResponse.data } returns mockTicketResponse

        runTest {
            coEvery { dataSource.sendTicket(any()) } returns mockResponse

            ticketRepository.sendTicket(mockTicket)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(250)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Success)
                    coVerify {
                        dataSource.sendTicket(any())
                    }
                }
        }
    }

    @Test
    fun sendTicketError() {
        runTest {
            coEvery { dataSource.sendTicket(any()) } throws IllegalStateException("Mock Error")

            ticketRepository.sendTicket(mockTicket)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(250)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Error)
                    coVerify {
                        dataSource.sendTicket(any())
                    }
                }
        }
    }
}
