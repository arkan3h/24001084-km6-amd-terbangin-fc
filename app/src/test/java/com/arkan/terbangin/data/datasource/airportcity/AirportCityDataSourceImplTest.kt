package com.arkan.terbangin.data.datasource.airportcity

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.airport.AirportData
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

class AirportCityDataSourceImplTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: AirportCityDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = AirportCityDataSourceImpl(services)
    }

    @Test
    fun getAllAirportCity() {
        runTest {
            val mockResponse = mockk<Response<List<AirportData>?>>(relaxed = true)
            coEvery {
                services.getAllAirport()
            } returns mockResponse

            val actualResult = dataSource.getAllAirportCity()
            coVerify {
                services.getAllAirport()
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
