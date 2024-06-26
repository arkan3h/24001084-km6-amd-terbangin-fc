package com.arkan.terbangin.presentation.profile

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository

class ProfileViewModel(
    private val pref: UserPreferenceRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()

    fun doLogout() {
        pref.clearUserID()
        pref.clearToken()
    }
}
