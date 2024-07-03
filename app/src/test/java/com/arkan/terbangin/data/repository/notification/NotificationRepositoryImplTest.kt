package com.arkan.terbangin.data.repository.notification

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.notification.NotificationDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.notification.NotificationData
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

class NotificationRepositoryImplTest {
    @MockK
    lateinit var ds: NotificationDataSource

    private lateinit var repo: NotificationRepository

    private val notification1 =
        NotificationData(
            id = "1",
            bookingId = "1",
            userId = "1",
            deletedAt = "1",
            createdAt = "1",
            updatedAt = "1",
            message = "1",
            title = "1",
            statusRead = true,
        )
    private val notification2 =
        NotificationData(
            id = "2",
            bookingId = "2",
            userId = "2",
            deletedAt = "2",
            createdAt = "2",
            updatedAt = "2",
            message = "2",
            title = "2",
            statusRead = true,
        )
    private val mockListNotification = listOf(notification1, notification2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = NotificationRepositoryImpl(ds)
    }

    @Test
    fun getNotificationByUIDLoading() {
        val mockResponse = mockk<Response<List<NotificationData>?>>()
        every { mockResponse.data } returns mockListNotification

        runTest {
            coEvery {
                ds.getNotificationByUID(any())
            } returns mockResponse

            repo.getNotificationByUID("").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getNotificationByUID(any())
                }
            }
        }
    }

    @Test
    fun getNotificationByUIDSuccess() {
        val mockResponse = mockk<Response<List<NotificationData>?>>()
        every { mockResponse.data } returns mockListNotification

        runTest {
            coEvery {
                ds.getNotificationByUID(any())
            } returns mockResponse

            repo.getNotificationByUID("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getNotificationByUID(any())
                }
            }
        }
    }

    @Test
    fun getNotificationByUIDEmpty() {
        val mockListNotification = listOf<NotificationData>()
        val mockResponse = mockk<Response<List<NotificationData>?>>()
        every { mockResponse.data } returns mockListNotification

        runTest {
            coEvery {
                ds.getNotificationByUID(any())
            } returns mockResponse

            repo.getNotificationByUID("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getNotificationByUID(any())
                }
            }
        }
    }

    @Test
    fun getNotificationByUIDError() {
        runTest {
            coEvery {
                ds.getNotificationByUID(any())
            } throws IllegalStateException("Mock Error")

            repo.getNotificationByUID("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getNotificationByUID(any())
                }
            }
        }
    }

    @Test
    fun readNotificationLoading() {
        val mockResponse = mockk<Response<NotificationData?>>()
        every { mockResponse.data } returns notification1

        runTest {
            coEvery {
                ds.readNotification(any())
            } returns mockResponse

            repo.readNotification("").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.readNotification(any())
                }
            }
        }
    }

    @Test
    fun readNotificationSuccess() {
        val mockResponse = mockk<Response<NotificationData?>>()
        every { mockResponse.data } returns notification1

        runTest {
            coEvery {
                ds.readNotification(any())
            } returns mockResponse

            repo.readNotification("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.readNotification(any())
                }
            }
        }
    }

    @Test
    fun readNotificationError() {
        runTest {
            coEvery {
                ds.readNotification(any())
            } throws IllegalStateException("Mock Error")

            repo.readNotification("").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.readNotification(any())
                }
            }
        }
    }
}
