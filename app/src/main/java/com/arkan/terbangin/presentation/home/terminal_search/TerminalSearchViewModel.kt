package com.arkan.terbangin.presentation.home.terminal_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.repository.airport.AirportCityRepository
import com.arkan.terbangin.data.repository.searchhistory.SearchTerminalRepository
import com.arkan.terbangin.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TerminalSearchViewModel(
    private val repository: AirportCityRepository,
    private val historyRepo: SearchTerminalRepository,
) : ViewModel() {
    private val _searchResults = MutableLiveData<ResultWrapper<List<Airport>>>()
    val searchResults: LiveData<ResultWrapper<List<Airport>>> get() = _searchResults

    fun searchCities(query: String) {
        viewModelScope.launch {
            repository.searchAirportCity(query).collect {
                _searchResults.postValue(it)
            }
        }
    }

    fun saveSearchHistory(query: String): LiveData<ResultWrapper<Boolean>> {
        Log.d("History", "saveSearchHistory: $query")
        return query.let {
            historyRepo.insertSearchHistory(query).asLiveData(Dispatchers.IO)
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepo.deleteSearchHistory(searchHistory).collect()
        }
    }

    fun getSearchHistory() = historyRepo.getSearchHistory().asLiveData(Dispatchers.IO)

    fun clearSearchHistory() {
        viewModelScope.launch {
            historyRepo.clearSearchHistory()
        }
    }
}
