package com.arkan.terbangin.presentation.home.terminal_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkan.terbangin.data.model.AirportCity
import com.arkan.terbangin.data.repository.AirportCityRepository
import com.arkan.terbangin.utils.ResultWrapper
import kotlinx.coroutines.launch

class TerminalSearchViewModel(
    private val repository: AirportCityRepository,
) : ViewModel() {
    private val _searchResults = MutableLiveData<ResultWrapper<List<AirportCity>>>()
    val searchResults: LiveData<ResultWrapper<List<AirportCity>>> get() = _searchResults

    init {
        fetchAllCities()
    }

    private fun fetchAllCities() {
        viewModelScope.launch {
            repository.getAllAirportCity().collect {
                _searchResults.postValue(it)
            }
        }
    }

    fun searchCities(query: String) {
        viewModelScope.launch {
            repository.searchAirportCity(query).collect {
                _searchResults.postValue(it)
            }
        }
    }
}
