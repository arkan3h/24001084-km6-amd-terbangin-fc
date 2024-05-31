package com.arkan.terbangin.data.datasource.auth.requestotp

import com.arkan.terbangin.data.source.network.model.requestotp.RequestOTPResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.RequestBody

class RequestOTPApiDataSource(
    private val services: TerbanginApiServices,
) : RequestOTPDataSource {
    override suspend fun requestOTP(email: RequestBody): RequestOTPResponse {
        return services.requestOTP(email)
    }
}
