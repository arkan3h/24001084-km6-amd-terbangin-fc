package com.arkan.terbangin.data.datasource.auth.register

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterData
import okhttp3.RequestBody

interface RegisterDataSource {
    suspend fun doRegister(
        fullName: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
        password: RequestBody,
    ): Response<RegisterData?>
}
