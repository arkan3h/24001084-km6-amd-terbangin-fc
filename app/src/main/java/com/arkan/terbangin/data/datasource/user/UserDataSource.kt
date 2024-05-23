package com.arkan.terbangin.data.datasource.user

import com.arkan.terbangin.data.source.pref.UserPreference

interface UserDataSource {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)
}

class UserPreferenceDataSource(private val userPreference: UserPreference) : UserDataSource {
    override fun onBoardingState(): Boolean {
        return userPreference.onBoardingState()
    }

    override fun setOnBoarding(state: Boolean) {
        return userPreference.setOnBoarding(state)
    }
}
