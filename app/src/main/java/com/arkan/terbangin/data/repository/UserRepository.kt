package com.arkan.terbangin.data.repository

import com.arkan.terbangin.data.datasource.user.UserDataSource

interface UserRepository {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)

    fun isUsingDarkMode(): Boolean

    fun setUsingDarkMode(isUsingDarkMode: Boolean)

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearToken()
}

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {
    override fun onBoardingState(): Boolean {
        return userDataSource.onBoardingState()
    }

    override fun setOnBoarding(state: Boolean) {
        userDataSource.setOnBoarding(state)
    }

    override fun isUsingDarkMode(): Boolean {
        return userDataSource.isUsingDarkMode()
    }

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        userDataSource.setUsingDarkMode(isUsingDarkMode)
    }

    override fun saveToken(token: String) {
        userDataSource.saveToken(token)
    }

    override fun getToken(): String? {
        return userDataSource.getToken()
    }

    override fun clearToken() {
        userDataSource.clearToken()
    }
}
