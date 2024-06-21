package com.arkan.terbangin.data.datasource.profile

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.profile.ProfileData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileApiDataSource(
    private val services: TerbanginApiServices,
) : ProfileDataSource {
    override suspend fun getProfile(id: String): Response<ProfileData?> {
        return services.getProfile(id = id)
    }

    override suspend fun updateProfile(
        id: String,
        fullName: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
        picture: MultipartBody.Part?,
    ): Response<ProfileData?> {
        return services.updateProfile(
            id = id,
            fullName = fullName,
            email = email,
            phoneNumber = phoneNumber,
            picture = picture,
        )
    }

    override suspend fun deleteProfile(id: String): Response<ProfileData?> {
        return services.deleteProfile(id = id)
    }
}
