package com.arkan.terbangin.data.datasource.profile

import com.arkan.terbangin.data.source.network.model.profile.ProfileResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class ProfileApiDataSource(
    private val services: TerbanginApiServices,
) : ProfileDataSource {
    override suspend fun getProfile(id: String): ProfileResponse {
        return services.getProfile(id = id)
    }
}
