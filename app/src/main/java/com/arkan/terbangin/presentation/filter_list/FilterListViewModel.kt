package com.arkan.terbangin.presentation.filter_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.model.FilterList

class FilterListViewModel : ViewModel() {
    private val _selectedFilterList = MutableLiveData<FilterList>()
    val selectedFilterListClass: LiveData<FilterList> get() = _selectedFilterList

    fun saveSelectedFilterListClass(filterList: FilterList) {
        _selectedFilterList.value = filterList
    }
}