package com.arkan.terbangin.data.repository.auth

import com.arkan.terbangin.data.datasource.auth.register.RegisterDataSource
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterResponse
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
    ): Flow<ResultWrapper<RegisterResponse>>
}

class RegisterRepositoryImpl(
    private val dataSource: RegisterDataSource,
) : RegisterRepository {
    override fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Flow<ResultWrapper<RegisterResponse>> {
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
