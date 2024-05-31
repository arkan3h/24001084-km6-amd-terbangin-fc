package com.arkan.terbangin.data.datasource.auth.otp

import com.arkan.terbangin.data.source.network.model.otp.request_otp.RequestOTPResponse
import com.arkan.terbangin.data.source.network.model.otp.verify_otp.VerifyOTPResponse
import okhttp3.RequestBody

interface OTPDataSource {
    suspend fun requestOTP(email: RequestBody): RequestOTPResponse

    suspend fun verifyOTP(
        email: RequestBody,
        otp: RequestBody,
    ): VerifyOTPResponse
}
