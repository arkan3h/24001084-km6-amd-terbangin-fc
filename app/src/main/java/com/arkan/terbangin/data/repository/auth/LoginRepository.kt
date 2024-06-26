package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.login.LoginDataSource
import com.arkan.terbangin.data.datasource.preference.PreferenceDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.login.LoginData
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.createPartFromString
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Response<LoginData?>>>
}

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource,
    private val preferenceDataSource: PreferenceDataSource,
) : LoginRepository {
    override fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Response<LoginData?>>> {
        return proceedFlow {
            val emailBody = createPartFromString(email)
            val passwordBody = createPartFromString(password)
            val loginResponse = dataSource.doLogin(emailBody, passwordBody)

            loginResponse.data?.token?.let {
                preferenceDataSource.saveToken(it)
            }
            loginResponse.data?.user?.id?.let {
                preferenceDataSource.saveIDUser(it)
            }

            loginResponse
        }
    }
}
