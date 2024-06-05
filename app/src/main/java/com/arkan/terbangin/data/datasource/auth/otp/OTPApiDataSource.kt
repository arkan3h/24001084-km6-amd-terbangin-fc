package com.arkan.terbangin.data.datasource.auth.otp

import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPResponse
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.RequestBody

class OTPApiDataSource(
    private val services: TerbanginApiServices,
) : OTPDataSource {
    override suspend fun requestOTP(email: RequestBody): RequestOTPResponse {
        return services.requestOTP(email)
    }

    override suspend fun verifyOTP(
        email: RequestBody,
        otp: RequestBody,
    ): VerifyOTPResponse {
        return services.verifyOTP(email, otp)
    }
}
