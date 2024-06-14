package com.arkan.terbangin.presentation.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.searchhistory.SearchHistoryRepository
import com.arkan.terbangin.data.source.AppDatabase
import kotlinx.coroutines.launch

class HistoryViewModel(
    application: Application,
    pref: UserPreferenceRepository,
) : AndroidViewModel(application) {
    val isLoggedIn = pref.getToken()
    private val repository: SearchHistoryRepository

    init {
        val searchHistoryDao = AppDatabase.getDatabase(application).searchHistoryDao()
        repository = SearchHistoryRepository(searchHistoryDao)
    }

    fun saveSearchHistory(query: String) {
        viewModelScope.launch {
            repository.insertSearchHistory(query)
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            // Handle fetching search history here
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearSearchHistory()
        }
    }
}
