package com.arkan.terbangin.presentation.history.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.history.HistoryRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers

class DetailHistoryViewModel(
    private val pref: UserPreferenceRepository,
    private val repository: HistoryRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()

    fun getUserID() = pref.getUserID()

    fun getDetailHistoryByUUID(id: String) =
        repository.getHistoryData(id).asLiveData(
            Dispatchers.IO,
        )
}
