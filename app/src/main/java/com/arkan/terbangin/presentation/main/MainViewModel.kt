package com.arkan.terbangin.presentation.main

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository

class MainViewModel(
    private val userPreferenceRepository: UserPreferenceRepository,
) : ViewModel() {
    fun setOnBoarding(state: Boolean) = userPreferenceRepository.setOnBoarding(state)
}
