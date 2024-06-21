package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.otp.OTPDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPData
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPData
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.createPartFromString
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface OTPRepository {
    fun requestOTP(email: String): Flow<ResultWrapper<Response<RequestOTPData?>>>

    fun verifyOTP(
        email: String,
        otp: String,
    ): Flow<ResultWrapper<Response<VerifyOTPData?>>>
}

class OTPRepositoryImpl(
    private val dataSource: OTPDataSource,
) : OTPRepository {
    override fun requestOTP(email: String): Flow<ResultWrapper<Response<RequestOTPData?>>> {
        return proceedFlow {
            val emailBody = createPartFromString(email)

            dataSource.requestOTP(emailBody)
        }
    }

    override fun verifyOTP(
        email: String,
        otp: String,
    ): Flow<ResultWrapper<Response<VerifyOTPData?>>> {
        return proceedFlow {
            val emailBody = createPartFromString(email)
            val otpBody = createPartFromString(otp)

            dataSource.verifyOTP(emailBody, otpBody)
        }
    }
}
