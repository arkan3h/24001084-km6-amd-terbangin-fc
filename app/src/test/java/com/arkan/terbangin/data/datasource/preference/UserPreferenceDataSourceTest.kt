package com.arkan.terbangin.data.datasource.preference

import com.arkan.terbangin.data.source.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceTest {
    @MockK
    lateinit var userPreference: UserPreference

    private lateinit var dataSource: PreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = UserPreferenceDataSource(userPreference)
    }

    @Test
    fun onBoardingState() {
        every { userPreference.onBoardingState() } returns true

        val actualResult = dataSource.onBoardingState()

        verify { userPreference.onBoardingState() }
        assertEquals(true, actualResult)
    }

    @Test
    fun setOnBoarding() {
        every { userPreference.setOnBoarding(any()) } returns Unit

        dataSource.setOnBoarding(true)

        verify { userPreference.setOnBoarding(true) }
    }

    @Test
    fun isUsingDarkMode() {
        every { userPreference.isUsingDarkMode() } returns true

        val actualResult = dataSource.isUsingDarkMode()

        verify { userPreference.isUsingDarkMode() }
        assertEquals(true, actualResult)
    }

    @Test
    fun setUsingDarkMode() {
        every { userPreference.setUsingDarkMode(any()) } returns Unit

        dataSource.setUsingDarkMode(true)

        verify { userPreference.setUsingDarkMode(true) }
    }

    @Test
    fun saveToken() {
        every { userPreference.saveToken(any()) } returns Unit

        dataSource.saveToken("testToken")

        verify { userPreference.saveToken("testToken") }
    }

    @Test
    fun getToken() {
        every { userPreference.getToken() } returns "testToken"

        val actualResult = dataSource.getToken()

        verify { userPreference.getToken() }
        assertEquals("testToken", actualResult)
    }

    @Test
    fun clearToken() {
        every { userPreference.clearToken() } returns Unit

        dataSource.clearToken()

        verify { userPreference.clearToken() }
    }

    @Test
    fun saveIDUser() {
        every { userPreference.saveIDUser(any()) } returns Unit

        dataSource.saveIDUser("userID")

        verify { userPreference.saveIDUser("userID") }
    }

    @Test
    fun getUserID() {
        every { userPreference.getUserID() } returns "userID"

        val actualResult = dataSource.getUserID()

        verify { userPreference.getUserID() }
        assertEquals("userID", actualResult)
    }

    @Test
    fun clearUserID() {
        every { userPreference.clearUserID() } returns Unit

        dataSource.clearUserID()

        verify { userPreference.clearUserID() }
    }

    @Test
    fun clearAll() {
        every { userPreference.clearAll() } returns Unit

        dataSource.clearAll()

        verify { userPreference.clearAll() }
    }
}
