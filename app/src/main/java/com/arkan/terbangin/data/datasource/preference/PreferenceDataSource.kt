package com.arkan.terbangin.data.datasource.preference

import com.arkan.terbangin.data.source.pref.UserPreference

interface PreferenceDataSource {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)

    fun isUsingDarkMode(): Boolean

    fun setUsingDarkMode(isUsingDarkMode: Boolean)

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearToken()

    fun saveIDUser(id: String)

    fun getUserID(): String?

    fun clearUserID()
}

class UserPreferenceDataSource(private val userPreference: UserPreference) : PreferenceDataSource {
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

    override fun saveIDUser(id: String) {
        userPreference.saveIDUser(id)
    }

    override fun getUserID(): String? {
        return userPreference.getUserID()
    }

    override fun clearUserID() {
        userPreference.clearUserID()
    }
}
