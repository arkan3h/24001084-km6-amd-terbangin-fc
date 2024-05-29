package com.arkan.terbangin.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.RegisterRepository
import com.arkan.terbangin.data.source.network.model.register.RegisterResponse
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(
    private val repository: RegisterRepository,
) : ViewModel() {
    private val _apiResponse = MutableLiveData<RegisterResponse?>()
    val apiResponse: LiveData<RegisterResponse?> get() = _apiResponse

    fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) = repository.doRegister(
        fullName,
        email,
        phoneNumber,
        password,
    ).asLiveData(Dispatchers.IO)
}
