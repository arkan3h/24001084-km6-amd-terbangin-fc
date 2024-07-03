package com.arkan.terbangin.data.datasource.notification

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.notification.NotificationData
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

class NotificationApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: NotificationDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = NotificationApiDataSource(services)
    }

    @Test
    fun getNotificationByUID() {
        runTest {
            val mockResponse = mockk<Response<List<NotificationData>?>>(relaxed = true)

            coEvery {
                services.getNotificationByUID(any())
            } returns mockResponse

            val actualResult = dataSource.getNotificationByUID("id")

            coVerify {
                services.getNotificationByUID("id")
            }

            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun readNotification() {
        runTest {
            val mockResponse = mockk<Response<NotificationData?>>(relaxed = true)

            coEvery {
                services.readNotification(any())
            } returns mockResponse

            val actualResult = dataSource.readNotification("id")

            coVerify {
                services.readNotification("id")
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
