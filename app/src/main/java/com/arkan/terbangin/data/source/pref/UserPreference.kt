package com.arkan.terbangin.data.source.pref

import android.content.SharedPreferences
import com.arkan.terbangin.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)

    fun isUsingDarkMode(): Boolean

    fun setUsingDarkMode(isUsingDarkMode: Boolean)
}

class UserPreferenceImpl(
    private val pref: SharedPreferences,
) : UserPreference {
    override fun onBoardingState(): Boolean = pref.getBoolean(KEY_ON_BOARDING_STATE, false)

    override fun isUsingDarkMode(): Boolean = pref.getBoolean(KEY_IS_USING_DARK_MODE, false)

    override fun setOnBoarding(state: Boolean) {
        pref[KEY_ON_BOARDING_STATE] = state
    }

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        pref[KEY_IS_USING_DARK_MODE] = isUsingDarkMode
    }


    companion object {
        const val PREF_NAME = "terbangin-pref"
        const val KEY_ON_BOARDING_STATE = "KEY_ON_BOARDING_STATE"
        const val KEY_IS_USING_DARK_MODE = "KEY_IS_USING_DARK_MODE"
    }
}
