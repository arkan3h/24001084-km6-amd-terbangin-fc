package com.arkan.terbangin.data.repository.auth

import android.util.Log
import com.arkan.terbangin.data.datasource.auth.login.LoginDataSource
import com.arkan.terbangin.data.source.network.model.login.LoginResponse
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

interface LoginRepository {
    fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<LoginResponse>>
}

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource,
) : LoginRepository {
    override fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<LoginResponse>> {
        Log.d("UserRepository", "Sending form with data: email=$email")
        return proceedFlow {
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

            dataSource.doLogin(emailBody, passwordBody)
        }
    }
}