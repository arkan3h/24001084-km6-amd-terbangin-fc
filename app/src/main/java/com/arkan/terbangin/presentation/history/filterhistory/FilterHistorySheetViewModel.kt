package com.arkan.terbangin.presentation.history.filterhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.StatusPayment

class FilterHistorySheetViewModel : ViewModel() {
    private val _selectedStatus = MutableLiveData<StatusPayment>()
    val selectedStatus: LiveData<StatusPayment> get() = _selectedStatus

    fun saveSelectedStatus(status: StatusPayment) {
        _selectedStatus.value = status
    }
}
