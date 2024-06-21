package com.arkan.terbangin.data.datasource.auth.otp

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPData
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPData
import okhttp3.RequestBody

interface OTPDataSource {
    suspend fun requestOTP(email: RequestBody): Response<RequestOTPData?>

    suspend fun verifyOTP(
        email: RequestBody,
        otp: RequestBody,
    ): Response<VerifyOTPData?>
}
