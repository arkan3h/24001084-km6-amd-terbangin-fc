package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.resetpassword.ResetPasswordDataSource
import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.createPartFromString
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ResetPasswordRepository {
    fun doResetPassword(email: String): Flow<ResultWrapper<ResetPasswordResponse>>
}

class ResetPasswordRepositoryImpl(
    private val dataSource: ResetPasswordDataSource,
) : ResetPasswordRepository {
    override fun doResetPassword(email: String): Flow<ResultWrapper<ResetPasswordResponse>> {
        return proceedFlow {
            val emailBody = createPartFromString(email)

            dataSource.doResetPassword(emailBody)
        }
    }
}
