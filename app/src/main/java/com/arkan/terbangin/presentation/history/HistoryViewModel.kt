package com.arkan.terbangin.presentation.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.StatusPayment
import com.arkan.terbangin.data.repository.history.HistoryRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers

class HistoryViewModel(
    private val pref: UserPreferenceRepository,
    private val repository: HistoryRepository,
) : ViewModel() {
    private var filterStatus: String = ""
    val isLoggedIn = pref.getToken()

    fun getUserID() = pref.getUserID()

    fun getHistoryByUUID(id: String) =
        repository.getHistoryData(id).asLiveData(
            Dispatchers.IO,
        )

    private val _selectedStatus = MutableLiveData<StatusPayment>()

    fun saveSelectedStatus(status: StatusPayment) {
        _selectedStatus.value = status
        setFilterStatusPayment(status)
    }

    private fun setFilterStatusPayment(status: StatusPayment) {
        when (status.statusPayment) {
            "Issued" -> {
                filterStatus = "Issued"
            }
            "Unpaid" -> {
                filterStatus = "Unpaid"
            }
            "Canceled" -> {
                filterStatus = "Canceled"
            }
        }
    }
}
