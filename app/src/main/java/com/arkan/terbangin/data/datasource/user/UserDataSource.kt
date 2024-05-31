package com.arkan.terbangin.data.datasource.user

import com.arkan.terbangin.data.source.pref.UserPreference

interface UserDataSource {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)

    fun isUsingDarkMode(): Boolean

    fun setUsingDarkMode(isUsingDarkMode: Boolean)

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearToken()
}

class UserPreferenceDataSource(private val userPreference: UserPreference) : UserDataSource {
    override fun onBoardingState(): Boolean {
        return userPreference.onBoardingState()
    }

    override fun setOnBoarding(state: Boolean) {
        return userPreference.setOnBoarding(state)
    }

    override fun isUsingDarkMode(): Boolean {
        return userPreference.isUsingDarkMode()
    }

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        userPreference.setUsingDarkMode(isUsingDarkMode)
    }

    override fun saveToken(token: String) {
        userPreference.saveToken(token)
    }

    override fun getToken(): String? {
        return userPreference.getToken()
    }

    override fun clearToken() {
        userPreference.clearToken()
    }
}
