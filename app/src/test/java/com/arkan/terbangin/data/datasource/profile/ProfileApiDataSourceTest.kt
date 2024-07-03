package com.arkan.terbangin.data.datasource.profile

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.profile.ProfileData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProfileApiDataSourceTest {
    @MockK
    lateinit var services: TerbanginApiServices

    private lateinit var dataSource: ProfileDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = ProfileApiDataSource(services)
    }

    @Test
    fun getProfile() {
        runTest {
            val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)

            coEvery {
                services.getProfile(any())
            } returns mockResponse

            val actualResult = dataSource.getProfile("id")

            coVerify {
                services.getProfile("id")
            }

            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)

            val fullName = mockk<RequestBody>()
            val email = mockk<RequestBody>()
            val phoneNumber = mockk<RequestBody>()
            val picture = mockk<MultipartBody.Part>()

            coEvery {
                services.updateProfile(any(), any(), any(), any(), any())
            } returns mockResponse

            val actualResult =
                dataSource.updateProfile(
                    "id",
                    fullName,
                    email,
                    phoneNumber,
                    picture,
                )

            coVerify {
                services.updateProfile("id", fullName, email, phoneNumber, picture)
            }

            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun deleteProfile() {
        runTest {
            val mockResponse = mockk<Response<ProfileData?>>(relaxed = true)

            coEvery {
                services.deleteProfile(any())
            } returns mockResponse

            val actualResult = dataSource.deleteProfile("id")

            coVerify {
                services.deleteProfile("id")
            }

            assertEquals(actualResult, mockResponse)
        }
    }
}
