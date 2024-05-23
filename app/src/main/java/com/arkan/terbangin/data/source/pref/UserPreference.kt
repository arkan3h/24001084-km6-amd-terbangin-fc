package com.arkan.terbangin.data.source.pref

import android.content.SharedPreferences
import com.arkan.terbangin.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun onBoardingState(): Boolean

    fun setOnBoarding(state: Boolean)

//    fun isUsingGrid(): Boolean
//
//    fun setUsingGridMode(isUsingGrid: Boolean)
}

class UserPreferenceImpl(
    private val pref: SharedPreferences,
) : UserPreference {
    override fun onBoardingState(): Boolean = pref.getBoolean(KEY_ON_BOARDING_STATE, false)

    override fun setOnBoarding(state: Boolean) {
        pref[KEY_ON_BOARDING_STATE] = state
    }

//    override fun isUsingGrid(): Boolean = pref.getBoolean(KEY_IS_USING_GRID, false)
//
//    override fun setUsingGridMode(isUsingGrid: Boolean) {
//        pref[KEY_IS_USING_GRID] = isUsingGrid
//    }

    companion object {
        const val PREF_NAME = "terbangin-pref"
        const val KEY_ON_BOARDING_STATE = "KEY_ON_BOARDING_STATE"
//        const val KEY_IS_USING_GRID = "KEY_IS_USING_GRID"
    }
}
