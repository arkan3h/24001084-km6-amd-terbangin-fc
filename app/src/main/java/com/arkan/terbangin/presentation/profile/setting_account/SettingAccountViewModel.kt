package com.arkan.terbangin.presentation.profile.setting_account

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserPreferenceRepository

class SettingAccountViewModel(private val userPreferenceRepository: UserPreferenceRepository) : ViewModel() {
    fun isUsingDarkMode() = userPreferenceRepository.isUsingDarkMode()

    fun setUsingDarkMode(isUsingDarkMode: Boolean) = userPreferenceRepository.setUsingDarkMode(isUsingDarkMode)
}
