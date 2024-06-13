package com.arkan.terbangin.presentation.notification

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository

class NotificationViewModel(
    pref: UserPreferenceRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()
}
