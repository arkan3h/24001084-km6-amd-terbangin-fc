package com.arkan.terbangin.data.source.pref

import android.content.SharedPreferences
import com.arkan.terbangin.utils.SharedPreferenceUtils.set

interface UserPreference {
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

    override fun saveToken(token: String) {
        pref.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String? = pref.getString(KEY_TOKEN, null)

    override fun clearToken() {
        pref.edit().remove(KEY_TOKEN).apply()
    }

    override fun saveIDUser(id: String) {
        pref.edit().putString(KEY_ID_USER, id).apply()
    }

    override fun getUserID(): String? = pref.getString(KEY_ID_USER, null)

    override fun clearUserID() {
        pref.edit().remove(KEY_ID_USER).apply()
    }

    companion object {
        const val PREF_NAME = "terbangin-pref"
        const val KEY_ON_BOARDING_STATE = "KEY_ON_BOARDING_STATE"
        const val KEY_IS_USING_DARK_MODE = "KEY_IS_USING_DARK_MODE"
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_ID_USER = "KEY_ID_USER"
    }
}
