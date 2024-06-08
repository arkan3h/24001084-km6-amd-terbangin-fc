package com.arkan.terbangin.presentation.notification

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserPreferenceRepository

class NotificationViewModel(
    pref: UserPreferenceRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()
}
