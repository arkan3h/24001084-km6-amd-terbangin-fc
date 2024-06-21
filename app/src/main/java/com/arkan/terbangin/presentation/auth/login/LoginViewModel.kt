package com.arkan.terbangin.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.LoginRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(
    private val repository: LoginRepository,
) : ViewModel() {
    fun doLogin(
        email: String,
        password: String,
    ) = repository.doLogin(
        email,
        password,
    ).asLiveData(Dispatchers.IO)
}
