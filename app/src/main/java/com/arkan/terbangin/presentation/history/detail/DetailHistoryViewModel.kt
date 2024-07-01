package com.arkan.terbangin.presentation.history.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.data.repository.history.HistoryRepository
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers

class DetailHistoryViewModel(
    private val pref: UserPreferenceRepository,
    private val repository: HistoryRepository,
    private val profileRepository: ProfileRepository,
    extras: Bundle?,
) : ViewModel() {
    val isLoggedIn = pref.getToken()
    val history = extras?.getParcelable<History>(DetailHistoryActivity.EXTRA_ITEM_HISTORY)
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> get() = _profile

    fun getUserID() = pref.getUserID()

    fun getProfileData() = profileRepository.getProfile(getUserID()!!).asLiveData(Dispatchers.IO)

    fun saveProfile(profile: Profile) {
        _profile.value = profile
    }

    fun getDetailHistory(id: String) =
        repository.getDetailHistoryData(id).asLiveData(
            Dispatchers.IO,
        )
}
