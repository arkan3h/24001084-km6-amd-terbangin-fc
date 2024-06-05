package com.arkan.terbangin.data.datasource.auth.login

import com.arkan.terbangin.data.source.network.model.auth.login.LoginResponse
import okhttp3.RequestBody

interface LoginDataSource {
    suspend fun doLogin(
        email: RequestBody,
        password: RequestBody,
    ): LoginResponse
}
