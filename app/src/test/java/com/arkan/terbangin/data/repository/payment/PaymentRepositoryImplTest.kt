package com.arkan.terbangin.data.repository.payment

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.booking.BookingDataSource
import com.arkan.terbangin.data.datasource.booking.HelperBookingDataSource
import com.arkan.terbangin.data.datasource.payment.PaymentDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.booking.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.payment.PaymentData
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PaymentRepositoryImplTest {
    @MockK
    lateinit var ds: PaymentDataSource

    @MockK
    lateinit var dsBooking: BookingDataSource

    @MockK
    lateinit var dsHelperBooking: HelperBookingDataSource

    @MockK
    lateinit var pref: UserPreference

    private lateinit var repo: PaymentRepository

    private val mockPayment =
        PaymentData(
            id = "1",
            userId = "1",
            deletedAt = "1",
            totalPrice = 1,
            snapToken = "1",
            snapLink = "1",
            method = "1",
            status = "1",
            createdAt = "1",
            updatedAt = "1",
        )
    private val mockBooking =
        BookingDataResponse(
            id = "1",
            paymentId = "1",
            userId = "1",
            createdAt = "1",
            updatedAt = "1",
            status = "1",
            deletedAt = "1",
        )
    private val mockHelperBooking =
        HelperBookingDataResponse(
            id = "1",
            bookingId = "1",
            passangerId = "1",
            seatId = "1",
            deletedAt = "1",
            createdAt = "1",
            updatedAt = "1",
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = PaymentRepositoryImpl(ds, dsBooking, dsHelperBooking, pref)
    }

    @Test
    fun createPaymentLoading() {
        val mockPaymentResponse = mockk<Response<PaymentData?>>(relaxed = true)
        every { mockPaymentResponse.data } returns mockPayment

        val mockBookingResponse = mockk<Response<BookingDataResponse?>>(relaxed = true)
        every { mockBookingResponse.data } returns mockBooking

        val mockHelperBookingResponse = mockk<Response<HelperBookingDataResponse?>>(relaxed = true)
        every { mockHelperBookingResponse.data } returns mockHelperBooking

        runTest {
            coEvery { ds.createPayment(any()) } returns mockPaymentResponse
            coEvery { dsBooking.createBooking(any()) } returns mockBookingResponse
            coEvery { dsHelperBooking.createHelperBooking(any()) } returns mockHelperBookingResponse
            coEvery { pref.getUserID() } returns "user_id"

            repo.createPayment(
                totalPrice = 100,
                status = "One Way",
                passengerId = listOf("passenger1", "passenger2"),
                seatId = listOf("seat1", "seat2"),
                seatReturnId = emptyList(),
            ).map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)

                coVerifyOrder {
                    ds.createPayment(any())
                    pref.getUserID()
                    dsBooking.createBooking(any())
                    dsHelperBooking.createHelperBooking(any())
                }
            }
        }
    }

    @Test
    fun createPaymentSuccess() {
        val mockPaymentResponse = mockk<Response<PaymentData?>>(relaxed = true)
        every { mockPaymentResponse.data } returns mockPayment

        val mockBookingResponse = mockk<Response<BookingDataResponse?>>(relaxed = true)
        every { mockBookingResponse.data } returns mockBooking

        val mockHelperBookingResponse = mockk<Response<HelperBookingDataResponse?>>(relaxed = true)
        every { mockHelperBookingResponse.data } returns mockHelperBooking

        runTest {
            coEvery { ds.createPayment(any()) } returns mockPaymentResponse
            coEvery { dsBooking.createBooking(any()) } returns mockBookingResponse
            coEvery { dsHelperBooking.createHelperBooking(any()) } returns mockHelperBookingResponse
            coEvery { pref.getUserID() } returns "user_id"

            repo.createPayment(
                totalPrice = 100,
                status = "One Way",
                passengerId = listOf("passenger1", "passenger2"),
                seatId = listOf("seat1", "seat2"),
                seatReturnId = emptyList(),
            ).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)

                coVerifyOrder {
                    ds.createPayment(any())
                    pref.getUserID()
                    dsBooking.createBooking(any())
                    dsHelperBooking.createHelperBooking(any())
                }
            }
        }
    }

    @Test
    fun createPaymentError() {
        runTest {
            coEvery { ds.createPayment(any()) } throws IllegalStateException("Mock Error")

            repo.createPayment(
                totalPrice = 100,
                status = "One Way",
                passengerId = listOf("passenger1", "passenger2"),
                seatId = listOf("seat1", "seat2"),
                seatReturnId = emptyList(),
            ).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)

                coVerify(exactly = 1) {
                    ds.createPayment(any())
                }
                coVerify(exactly = 0) {
                    dsBooking.createBooking(any())
                    dsHelperBooking.createHelperBooking(any())
                    pref.getUserID()
                }
            }
        }
    }

    @Test
    fun createPaymentReturnLoading() {
        val mockPaymentResponse = mockk<Response<PaymentData?>>(relaxed = true)
        every { mockPaymentResponse.data } returns mockPayment

        val mockBookingResponse = mockk<Response<BookingDataResponse?>>(relaxed = true)
        every { mockBookingResponse.data } returns mockBooking

        val mockHelperBookingResponse = mockk<Response<HelperBookingDataResponse?>>(relaxed = true)
        every { mockHelperBookingResponse.data } returns mockHelperBooking

        runTest {
            coEvery { ds.createPayment(any()) } returns mockPaymentResponse
            coEvery { dsBooking.createBooking(any()) } returns mockBookingResponse
            coEvery { dsHelperBooking.createHelperBooking(any()) } returns mockHelperBookingResponse
            coEvery { pref.getUserID() } returns "user_id"

            repo.createPayment(
                totalPrice = 100,
                status = "Return",
                passengerId = listOf("passenger1", "passenger2"),
                seatId = listOf("seat1", "seat2"),
                seatReturnId = listOf("seat1", "seat2"),
            ).map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)

                coVerifyOrder {
                    ds.createPayment(any())
                    pref.getUserID()
                    dsBooking.createBooking(any())
                    dsHelperBooking.createHelperBooking(any())
                }
            }
        }
    }

    @Test
    fun createPaymentReturnSuccess() {
        val mockPaymentResponse = mockk<Response<PaymentData?>>(relaxed = true)
        every { mockPaymentResponse.data } returns mockPayment

        val mockBookingResponse = mockk<Response<BookingDataResponse?>>(relaxed = true)
        every { mockBookingResponse.data } returns mockBooking

        val mockHelperBookingResponse = mockk<Response<HelperBookingDataResponse?>>(relaxed = true)
        every { mockHelperBookingResponse.data } returns mockHelperBooking

        runTest {
            coEvery { ds.createPayment(any()) } returns mockPaymentResponse
            coEvery { dsBooking.createBooking(any()) } returns mockBookingResponse
            coEvery { dsHelperBooking.createHelperBooking(any()) } returns mockHelperBookingResponse
            coEvery { pref.getUserID() } returns "user_id"

            repo.createPayment(
                totalPrice = 100,
                status = "Return",
                passengerId = listOf("passenger1", "passenger2"),
                seatId = listOf("seat1", "seat2"),
                seatReturnId = listOf("seat1", "seat2"),
            ).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)

                coVerifyOrder {
                    ds.createPayment(any())
                    pref.getUserID()
                    dsBooking.createBooking(any())
                    dsHelperBooking.createHelperBooking(any())
                }
            }
        }
    }

    @Test
    fun createPaymentReturnError() {
        runTest {
            coEvery { ds.createPayment(any()) } throws IllegalStateException("Mock Error")

            repo.createPayment(
                totalPrice = 100,
                status = "Return",
                passengerId = listOf("passenger1", "passenger2"),
                seatId = listOf("seat1", "seat2"),
                seatReturnId = listOf("seat1", "seat2"),
            ).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)

                coVerify(exactly = 1) {
                    ds.createPayment(any())
                }
                coVerify(exactly = 0) {
                    dsBooking.createBooking(any())
                    dsHelperBooking.createHelperBooking(any())
                    pref.getUserID()
                }
            }
        }
    }
}
