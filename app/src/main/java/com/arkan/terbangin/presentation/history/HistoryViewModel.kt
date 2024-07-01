package com.arkan.terbangin.presentation.history

import androidx.lifecycle.LiveData
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
    private val _filter = MutableLiveData<StatusPayment>()
    val filter: LiveData<StatusPayment> get() = _filter

    fun getUserID() = pref.getUserID()

    fun getHistoryByUUID(
        id: String,
        query: String,
    ) = repository.getHistoryData(id, filterStatus, query).asLiveData(
        Dispatchers.IO,
    )

    fun saveSelectedStatus(status: StatusPayment) {
        _filter.value = status
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
                filterStatus = "Cancelled"
            }
        }
    }

    fun resetFilterStatus() {
        filterStatus = ""
    }
}
