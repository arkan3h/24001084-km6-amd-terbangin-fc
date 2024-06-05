package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.otp.OTPDataSource
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPResponse
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPResponse
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

interface OTPRepository {
    fun requestOTP(email: String): Flow<ResultWrapper<RequestOTPResponse>>

    fun verifyOTP(
        email: String,
        otp: String,
    ): Flow<ResultWrapper<VerifyOTPResponse>>
}

class OTPRepositoryImpl(
    private val dataSource: OTPDataSource,
) : OTPRepository {
    override fun requestOTP(email: String): Flow<ResultWrapper<RequestOTPResponse>> {
        return proceedFlow {
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())

            dataSource.requestOTP(emailBody)
        }
    }

    override fun verifyOTP(
        email: String,
        otp: String,
    ): Flow<ResultWrapper<VerifyOTPResponse>> {
        return proceedFlow {
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val otpBody = otp.toRequestBody("text/plain".toMediaTypeOrNull())

            dataSource.verifyOTP(emailBody, otpBody)
        }
    }
}
