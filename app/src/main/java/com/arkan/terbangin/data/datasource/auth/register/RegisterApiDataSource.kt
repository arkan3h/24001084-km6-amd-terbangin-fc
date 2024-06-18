package com.arkan.terbangin.data.datasource.auth.register

import com.arkan.terbangin.data.source.network.model.auth.register.RegisterResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import com.arkan.terbangin.utils.ErrorInterceptor
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
        try {
            return services.doRegister(
                fullName,
                email,
                phoneNumber,
                password,
            )
        } catch (e: ErrorInterceptor.NoInternetException) {
            throw Exception("No Internet Connection")
        } catch (e: ErrorInterceptor.HttpException) {
            throw Exception(e.message)
        }
    }
}
