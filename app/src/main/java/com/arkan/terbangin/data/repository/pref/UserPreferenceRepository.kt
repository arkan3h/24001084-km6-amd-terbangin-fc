package com.arkan.terbangin.data.repository.pref

import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource

interface UserPreferenceRepository {
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

    fun clearAll()
}

class UserPreferenceRepositoryImpl(private val preferenceDataSource: PreferenceDataSource) :
    UserPreferenceRepository {
    override fun onBoardingState(): Boolean {
        return preferenceDataSource.onBoardingState()
    }

    override fun setOnBoarding(state: Boolean) {
        preferenceDataSource.setOnBoarding(state)
    }

    override fun isUsingDarkMode(): Boolean {
        return preferenceDataSource.isUsingDarkMode()
    }

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        preferenceDataSource.setUsingDarkMode(isUsingDarkMode)
    }

    override fun saveToken(token: String) {
        preferenceDataSource.saveToken(token)
    }

    override fun getToken(): String? {
        return preferenceDataSource.getToken()
    }

    override fun clearToken() {
        preferenceDataSource.clearToken()
    }

    override fun saveIDUser(id: String) {
        preferenceDataSource.saveIDUser(id)
    }

    override fun getUserID(): String? {
        return preferenceDataSource.getUserID()
    }

    override fun clearUserID() {
        preferenceDataSource.clearUserID()
    }

    override fun clearAll() {
        preferenceDataSource.clearAll()
    }
}
