package com.arkan.terbangin.presentation.home

import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.repository.UserRepository

class HomeViewModel(
    private val pref: UserRepository,
) : ViewModel() {
    fun getToken() = pref.getToken()
}
