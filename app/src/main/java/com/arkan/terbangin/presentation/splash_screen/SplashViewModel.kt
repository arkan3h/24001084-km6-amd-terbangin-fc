package com.arkan.terbangin.presentation.splash_screen

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserRepository

class SplashViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun onBoardingState() = userRepository.onBoardingState()
}
