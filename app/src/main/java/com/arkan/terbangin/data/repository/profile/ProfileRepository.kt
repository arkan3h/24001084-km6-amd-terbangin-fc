package com.arkan.terbangin.data.repository.profile

import com.arkan.terbangin.data.datasource.profile.ProfileDataSource
import com.arkan.terbangin.data.mapper.toProfile
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(id: String): Flow<ResultWrapper<Profile>>
}

class ProfileRepositoryImpl(
    private val dataSource: ProfileDataSource,
) : ProfileRepository {
    override fun getProfile(id: String): Flow<ResultWrapper<Profile>> {
        return proceedFlow {
            dataSource.getProfile(id).toProfile()
        }
    }
}
