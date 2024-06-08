package com.arkan.terbangin.presentation.profile

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserPreferenceRepository

class ProfileViewModel(
    private val pref: UserPreferenceRepository,
) : ViewModel() {
    fun doLogout() {
        pref.clearUserID()
        pref.clearToken()
    }
}
