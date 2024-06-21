package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterData
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.createPartFromString
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<Response<RegisterData?>>>
}

class RegisterRepositoryImpl(
    private val dataSource: RegisterDataSource,
) : RegisterRepository {
    override fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<Response<RegisterData?>>> {
        return proceedFlow {
            dataSource.doRegister(
                createPartFromString(fullName),
                createPartFromString(email),
                createPartFromString(phoneNumber),
                createPartFromString(password),
            )
        }
    }
}
