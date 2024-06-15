package com.arkan.terbangin.presentation.history.searchhistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.repository.searchhistory.SearchHistoryRepository
import com.arkan.terbangin.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistorySearchViewModel(
    private val repository: SearchHistoryRepository,
) : ViewModel() {
    fun saveSearchHistory(query: String): LiveData<ResultWrapper<Boolean>> {
        Log.d("History", "saveSearchHistory: $query")
        return query.let {
            repository.insertSearchHistory(query).asLiveData(Dispatchers.IO)
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSearchHistory(searchHistory).collect()
        }
    }

    fun getSearchHistory() = repository.getSearchHistory().asLiveData(Dispatchers.IO)

    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearSearchHistory()
        }
    }
}
