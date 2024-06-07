package com.arkan.terbangin.data.datasource.profile

import com.arkan.terbangin.data.source.network.model.profile.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileDataSource {
    suspend fun getProfile(id: String): ProfileResponse

    suspend fun updateProfile(
        id: String,
        fullName: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
        picture: MultipartBody.Part?,
    ): ProfileResponse
}
