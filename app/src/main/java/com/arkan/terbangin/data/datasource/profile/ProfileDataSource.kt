package com.arkan.terbangin.data.datasource.profile

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.profile.ProfileData
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileDataSource {
    suspend fun getProfile(id: String): Response<ProfileData?>

    suspend fun updateProfile(
        id: String,
        fullName: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
        picture: MultipartBody.Part?,
    ): Response<ProfileData?>

    suspend fun deleteProfile(id: String): Response<ProfileData?>
}
