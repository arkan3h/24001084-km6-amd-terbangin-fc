package com.arkan.terbangin.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.notification.NotificationRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers

class NotificationViewModel(
    private val pref: UserPreferenceRepository,
    private val repository: NotificationRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()

    fun getUserID() = pref.getUserID()

    fun getNotificationByUID(id: String) =
        repository.getNotificationByUID(id).asLiveData(
            Dispatchers.IO,
        )

    fun readNotification(id: String) = repository.readNotification(id).asLiveData(Dispatchers.IO)
}
