package com.arkan.terbangin.data.repository.pref

import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceRepositoryImplTest {
    @MockK
    lateinit var ds: PreferenceDataSource

    private lateinit var repo: UserPreferenceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserPreferenceRepositoryImpl(ds)
    }

    @Test
    fun onBoardingState() {
        every { ds.onBoardingState() } returns true

        val state = repo.onBoardingState()
        assertTrue(state)
        verify { ds.onBoardingState() }
    }

    @Test
    fun setOnBoarding() {
        every { ds.setOnBoarding(true) } just runs

        repo.setOnBoarding(true)
        verify { ds.setOnBoarding(true) }
    }

    @Test
    fun isUsingDarkMode() {
        every { ds.isUsingDarkMode() } returns true

        val isDarkMode = repo.isUsingDarkMode()
        assertTrue(isDarkMode)
        verify { ds.isUsingDarkMode() }
    }

    @Test
    fun setUsingDarkMode() {
        every { ds.setUsingDarkMode(true) } just runs

        repo.setUsingDarkMode(true)
        verify { ds.setUsingDarkMode(true) }
    }

    @Test
    fun saveToken() {
        every { ds.saveToken("token123") } just runs

        repo.saveToken("token123")
        verify { ds.saveToken("token123") }
    }

    @Test
    fun getToken() {
        every { ds.getToken() } returns "token123"

        val token = repo.getToken()
        assertEquals("token123", token)
        verify { ds.getToken() }
    }

    @Test
    fun clearToken() {
        every { ds.clearToken() } just runs

        repo.clearToken()
        verify { ds.clearToken() }
    }

    @Test
    fun saveIDUser() {
        every { ds.saveIDUser("userID123") } just runs

        repo.saveIDUser("userID123")
        verify { ds.saveIDUser("userID123") }
    }

    @Test
    fun getUserID() {
        every { ds.getUserID() } returns "userID123"

        val userID = repo.getUserID()
        assertEquals("userID123", userID)
        verify { ds.getUserID() }
    }

    @Test
    fun clearUserID() {
        every { ds.clearUserID() } just runs

        repo.clearUserID()
        verify { ds.clearUserID() }
    }

    @Test
    fun clearAll() {
        every { ds.clearAll() } just runs

        repo.clearAll()
        verify { ds.clearAll() }
    }
}
