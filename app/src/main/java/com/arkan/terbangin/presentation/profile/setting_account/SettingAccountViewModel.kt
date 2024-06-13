package com.arkan.terbangin.presentation.profile.setting_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers

class SettingAccountViewModel(
    private val pref: UserPreferenceRepository,
    private val profileRepository: ProfileRepository,
) :
    ViewModel() {
    fun getUserID() = pref.getUserID()

    fun isUsingDarkMode() = pref.isUsingDarkMode()

    fun setUsingDarkMode(isUsingDarkMode: Boolean) = pref.setUsingDarkMode(isUsingDarkMode)

    fun doLogout() {
        pref.clearUserID()
        pref.clearToken()
    }

    fun deleteProfile(id: String) = profileRepository.deleteProfile(id).asLiveData(Dispatchers.IO)
}
