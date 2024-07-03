package com.arkan.terbangin.data.datasource.passenger

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponseData
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

class PassengerApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: PassengerDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = PassengerApiDataSource(services)
    }

    @Test
    fun createPassenger() {
        runTest {
            val mockPayload = mockk<PassengerPayload>()

            val mockResponse = mockk<Response<PassengerResponseData?>>(relaxed = true)

            coEvery {
                services.createPassenger(any())
            } returns mockResponse

            val actualResult = dataSource.createPassenger(mockPayload)

            coVerify {
                services.createPassenger(mockPayload)
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
