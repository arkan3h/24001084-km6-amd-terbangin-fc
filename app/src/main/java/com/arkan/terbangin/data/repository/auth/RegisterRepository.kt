package com.arkan.terbangin.data.repository.auth

import android.util.Log
import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.source.network.model.register.RegisterResponse
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

interface RegisterRepository {
    fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<RegisterResponse>>
}

class RegisterRepositoryImpl(
    private val dataSource: RegisterDataSource,
) : RegisterRepository {
    override fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<RegisterResponse>> {
        Log.d("UserRepository", "Sending form with data: fullName=$fullName, email=$email, phoneNumber=$phoneNumber")
        return proceedFlow {
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
            val fullNameBody = fullName.toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())

            dataSource.doRegister(fullNameBody, emailBody, phoneNumberBody, passwordBody)
        }
    }
}
