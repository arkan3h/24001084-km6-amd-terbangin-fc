package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordDataSource
import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

interface ResetPasswordRepository {
    fun doResetPassword(email: String): Flow<ResultWrapper<ResetPasswordResponse>>
}

class ResetPasswordRepositoryImpl(
    private val dataSource: ResetPasswordDataSource,
) : ResetPasswordRepository {
    override fun doResetPassword(email: String): Flow<ResultWrapper<ResetPasswordResponse>> {
        return proceedFlow {
            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())

            dataSource.doResetPassword(emailBody)
        }
    }
}
