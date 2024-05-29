package com.arkan.terbangin.data.datasource.auth.register

import com.arkan.terbangin.data.source.network.model.register.RegisterResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.RequestBody

class RegisterApiDataSource(
    private val services: TerbanginApiServices,
) : RegisterDataSource {
    override suspend fun doRegister(
        fullName: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
        password: RequestBody,
    ): RegisterResponse {
        return services.doRegister(
            fullName,
            email,
            phoneNumber,
            password,
        )
    }
}
