package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.requestotp.RequestOTPDataSource
import com.arkan.terbangin.data.source.network.model.requestotp.RequestOTPResponse
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

interface RequestOTPRepository {
    fun requestOTP(email: String): Flow<ResultWrapper<RequestOTPResponse>>
}

class RequestOTPRepositoryImpl(
    private val dataSource: RequestOTPDataSource,
) : RequestOTPRepository {
    override fun requestOTP(email: String): Flow<ResultWrapper<RequestOTPResponse>> {
        return proceedFlow {
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())

            dataSource.requestOTP(emailBody)
        }
    }
}
