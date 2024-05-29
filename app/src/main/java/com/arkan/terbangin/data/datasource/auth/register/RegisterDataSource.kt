package com.arkan.terbangin.data.datasource.auth.register

import com.arkan.terbangin.data.source.network.model.register.RegisterResponse
import okhttp3.RequestBody

interface RegisterDataSource {
    suspend fun doRegister(
        fullName: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
        password: RequestBody,
    ): RegisterResponse
}
