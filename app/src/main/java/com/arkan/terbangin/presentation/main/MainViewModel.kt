package com.arkan.terbangin.presentation.main

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserRepository

class MainViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun setOnBoarding(state: Boolean) = userRepository.setOnBoarding(state)
}
