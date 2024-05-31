package com.arkan.terbangin.data.datasource.auth.requestotp

import com.arkan.terbangin.data.source.network.model.requestotp.RequestOTPResponse
import okhttp3.RequestBody

interface RequestOTPDataSource {
    suspend fun requestOTP(email: RequestBody): RequestOTPResponse
}
