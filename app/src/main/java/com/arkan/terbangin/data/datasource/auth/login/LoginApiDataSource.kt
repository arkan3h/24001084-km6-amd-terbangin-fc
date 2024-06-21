package com.arkan.terbangin.data.datasource.auth.login

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.login.LoginData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.RequestBody

class LoginApiDataSource(
    private val services: TerbanginApiServices,
) : LoginDataSource {
    override suspend fun doLogin(
        email: RequestBody,
        password: RequestBody,
    ): Response<LoginData?> {
        return services.doLogin(
            email,
            password,
        )
    }
}
