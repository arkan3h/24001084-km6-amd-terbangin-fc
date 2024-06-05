package com.arkan.terbangin.presentation.home

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserPreferenceRepository

class HomeViewModel(
    private val pref: UserPreferenceRepository,
) : ViewModel() {
    fun getToken() = pref.getToken()
}
