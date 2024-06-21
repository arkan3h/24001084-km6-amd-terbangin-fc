package com.arkan.terbangin.data.datasource.auth.otp

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPData
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.RequestBody

class OTPApiDataSource(
    private val services: TerbanginApiServices,
) : OTPDataSource {
    override suspend fun requestOTP(email: RequestBody): Response<RequestOTPData?> {
        return services.requestOTP(email)
    }

    override suspend fun verifyOTP(
        email: RequestBody,
        otp: RequestBody,
    ): Response<VerifyOTPData?> {
        return services.verifyOTP(email, otp)
    }
}
