package com.arkan.terbangin.presentation.class_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.TicketClass

class ClassSheetViewModel : ViewModel() {
    private val _selectedTicketClass = MutableLiveData<TicketClass>()
    val selectedTicketClass: LiveData<TicketClass> get() = _selectedTicketClass

    fun saveSelectedTicketClass(ticketClass: TicketClass) {
        _selectedTicketClass.value = ticketClass
    }
}
