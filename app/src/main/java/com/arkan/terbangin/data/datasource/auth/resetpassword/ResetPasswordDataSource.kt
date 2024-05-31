package com.arkan.terbangin.data.datasource.auth.resetpassword

import com.arkan.terbangin.data.source.network.model.resetpassword.ResetPasswordResponse
import okhttp3.RequestBody

interface ResetPasswordDataSource {
    suspend fun doResetPassword(email: RequestBody): ResetPasswordResponse
}
