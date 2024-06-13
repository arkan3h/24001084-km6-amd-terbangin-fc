package com.arkan.terbangin.presentation.home.terminal_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.repository.airport.AirportCityRepository
import com.arkan.terbangin.utils.ResultWrapper
import kotlinx.coroutines.launch

class TerminalSearchViewModel(
    private val repository: AirportCityRepository,
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
}
