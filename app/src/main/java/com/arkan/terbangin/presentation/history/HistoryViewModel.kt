package com.arkan.terbangin.presentation.history

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository

class HistoryViewModel(
    pref: UserPreferenceRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()
    val userId = pref.getUserID()

}
