package com.arkan.terbangin.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.LoginRepository
import com.arkan.terbangin.data.source.network.model.auth.login.LoginResponse
import kotlinx.coroutines.Dispatchers

class LoginViewModel(
    private val repository: LoginRepository,
) : ViewModel() {
    private val _apiResponse = MutableLiveData<LoginResponse?>()
    val apiResponse: LiveData<LoginResponse?>
        get() = _apiResponse

    fun doLogin(
        email: String,
        password: String,
    ) = repository.doLogin(
        email,
        password,
    ).asLiveData(Dispatchers.IO)
}
