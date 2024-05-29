package com.arkan.terbangin.data.repository

import com.arkan.terbangin.data.datasource.user.UserDataSource

interface UserRepository {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)

    fun isUsingDarkMode(): Boolean

    fun setUsingDarkMode(isUsingDarkMode: Boolean)
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
}
