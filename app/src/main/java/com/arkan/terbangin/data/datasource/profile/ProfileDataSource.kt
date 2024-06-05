package com.arkan.terbangin.data.datasource.profile

import com.arkan.terbangin.data.source.network.model.profile.ProfileResponse

interface ProfileDataSource {
    suspend fun getProfile(id: String): ProfileResponse
}
