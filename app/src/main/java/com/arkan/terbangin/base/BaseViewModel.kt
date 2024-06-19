package com.arkan.terbangin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.searchhistory.SearchHistoryRepository
import kotlinx.coroutines.launch

class BaseViewModel(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
) : ViewModel() {
    fun clearSession() {
        userPreferenceRepository.clearToken()
        userPreferenceRepository.clearUserID()
        userPreferenceRepository.clearAll()
        viewModelScope.launch {
            searchHistoryRepository.clearSearchHistory()
        }
    }
}
