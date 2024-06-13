package com.arkan.terbangin.presentation.splash_screen

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository

class SplashViewModel(
    private val userPreferenceRepository: UserPreferenceRepository,
) : ViewModel() {
    fun onBoardingState() = userPreferenceRepository.onBoardingState()
}
