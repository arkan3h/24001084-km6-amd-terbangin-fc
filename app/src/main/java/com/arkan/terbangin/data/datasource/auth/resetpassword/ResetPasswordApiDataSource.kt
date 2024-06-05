package com.arkan.terbangin.data.datasource.auth.resetpassword

import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.RequestBody

class ResetPasswordApiDataSource(
    private val services: TerbanginApiServices,
) : ResetPasswordDataSource {
    override suspend fun doResetPassword(email: RequestBody): ResetPasswordResponse {
        return services.doResetPassword(email)
    }
}
