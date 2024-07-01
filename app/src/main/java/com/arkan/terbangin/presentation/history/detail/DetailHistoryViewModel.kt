package com.arkan.terbangin.presentation.history.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.data.repository.history.HistoryRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers

class DetailHistoryViewModel(
    private val pref: UserPreferenceRepository,
    private val repository: HistoryRepository,
    extras: Bundle?,
) : ViewModel() {
    val isLoggedIn = pref.getToken()
    val history = extras?.getParcelable<History>(DetailHistoryActivity.EXTRA_ITEM_HISTORY)

    fun getUserID() = pref.getUserID()

    fun getDetailHistory(id: String) =
        repository.getDetailHistoryData(id).asLiveData(
            Dispatchers.IO,
        )
}
