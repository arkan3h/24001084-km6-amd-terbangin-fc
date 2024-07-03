package com.arkan.terbangin.data.repository.profile

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.profile.ProfileDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.profile.ProfileData
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

class ProfileRepositoryImplTest {
    @MockK
    lateinit var ds: ProfileDataSource

    private lateinit var repo: ProfileRepository

    private val profile =
        ProfileData(
            id = "1",
            createdAt = "1",
            updatedAt = "1",
            phoneNumber = "1",
            picture = "1",
            fullName = "1",
            email = "1",
            password = "1",
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = ProfileRepositoryImpl(ds)
    }

    @Test
    fun getProfileLoading() {
        val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)
        every { mockResponse.data } returns profile

        runTest {
            coEvery { ds.getProfile("1") } returns mockResponse
            repo.getProfile("1").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.getProfile("1") }
            }
        }
    }

    @Test
    fun getProfileSuccess() {
        val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)
        every { mockResponse.data } returns profile

        runTest {
            coEvery { ds.getProfile("1") } returns mockResponse
            repo.getProfile("1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.getProfile("1") }
            }
        }
    }

    @Test
    fun getProfileError() {
        runTest {
            coEvery { ds.getProfile("1") } throws IllegalStateException("Mock Error")
            repo.getProfile("1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.getProfile("1") }
            }
        }
    }

    @Test
    fun updateProfileLoading() {
        val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)
        every { mockResponse.data } returns profile

        runTest {
            coEvery { ds.updateProfile(any(), any(), any(), any(), any()) } returns mockResponse
            repo.updateProfile("1", "John Doe", "john.doe@example.com", "123456789", null).map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.updateProfile("1", any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun updateProfileSuccess() {
        val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)
        every { mockResponse.data } returns profile

        runTest {
            coEvery { ds.updateProfile(any(), any(), any(), any(), any()) } returns mockResponse
            repo.updateProfile("1", "John Doe", "john.doe@example.com", "123456789", null).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.updateProfile("1", any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun updateProfileError() {
        runTest {
            coEvery { ds.updateProfile(any(), any(), any(), any(), any()) } throws IllegalStateException("Mock Error")
            repo.updateProfile("1", "John Doe", "john.doe@example.com", "123456789", null).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.updateProfile("1", any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun deleteProfileLoading() {
        val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)
        every { mockResponse.data } returns profile

        runTest {
            coEvery { ds.deleteProfile("1") } returns mockResponse
            repo.deleteProfile("1").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.deleteProfile("1") }
            }
        }
    }

    @Test
    fun deleteProfileSuccess() {
        val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)
        every { mockResponse.data } returns profile

        runTest {
            coEvery { ds.deleteProfile("1") } returns mockResponse
            repo.deleteProfile("1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.deleteProfile("1") }
            }
        }
    }

    @Test
    fun deleteProfileError() {
        runTest {
            coEvery { ds.deleteProfile("1") } throws IllegalStateException("Mock Error")
            repo.deleteProfile("1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.deleteProfile("1") }
            }
        }
    }
}
