package com.arkan.terbangin.presentation.profile.setting_account

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserRepository

class SettingAccountViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun isUsingDarkMode() = userRepository.isUsingDarkMode()

    fun setUsingDarkMode(isUsingDarkMode: Boolean) = userRepository.setUsingDarkMode(isUsingDarkMode)
}
