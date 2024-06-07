package com.arkan.terbangin.data.repository.profile

import com.arkan.terbangin.data.datasource.profile.ProfileDataSource
import com.arkan.terbangin.data.mapper.toProfile
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.createPartFromString
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

interface ProfileRepository {
    fun getProfile(id: String): Flow<ResultWrapper<Profile>>

    fun updateProfile(
        id: String,
        fullName: String,
        email: String,
        phoneNumber: String,
        picture: File?,
    ): Flow<ResultWrapper<Profile>>
}

class ProfileRepositoryImpl(
    private val dataSource: ProfileDataSource,
) : ProfileRepository {
    override fun getProfile(id: String): Flow<ResultWrapper<Profile>> {
        return proceedFlow {
            dataSource.getProfile(id).toProfile()
        }
    }

    override fun updateProfile(
        id: String,
        fullName: String,
        email: String,
        phoneNumber: String,
        picture: File?,
    ): Flow<ResultWrapper<Profile>> {
        return proceedFlow {
            val picturePart =
                picture?.let {
                    val requestFile = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("picture", it.name, requestFile)
                }
            dataSource.updateProfile(
                id,
                createPartFromString(fullName),
                createPartFromString(email),
                createPartFromString(phoneNumber),
                picturePart,
            ).toProfile()
        }
    }
}
