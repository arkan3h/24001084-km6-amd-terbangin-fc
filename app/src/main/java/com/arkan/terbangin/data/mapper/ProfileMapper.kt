package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.profile.ProfileData

fun Response<ProfileData?>.toProfile() =
    Profile(
        id = this.data?.id.orEmpty(),
        fullName = this.data?.fullName.orEmpty(),
        email = this.data?.email.orEmpty(),
        phoneNumber = this.data?.phoneNumber.orEmpty(),
        password = this.data?.password.orEmpty(),
        picture = this.data?.picture.orEmpty(),
        createdAt = this.data?.createdAt.orEmpty(),
        updatedAt = this.data?.updatedAt.orEmpty(),
    )
