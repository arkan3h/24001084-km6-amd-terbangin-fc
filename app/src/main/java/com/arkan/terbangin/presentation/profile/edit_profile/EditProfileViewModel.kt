package com.arkan.terbangin.presentation.profile.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.UserPreferenceRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers
import java.io.File

class EditProfileViewModel(
    private val pref: UserPreferenceRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    fun getUserID() = pref.getUserID()

    fun getProfile(id: String) = profileRepository.getProfile(id).asLiveData(Dispatchers.IO)

    fun updateProfile(
        id: String,
        fullName: String,
        email: String,
        phoneNumber: String,
        picture: File?,
    ) = profileRepository.updateProfile(id, fullName, email, phoneNumber, picture)
        .asLiveData(Dispatchers.IO)
}
