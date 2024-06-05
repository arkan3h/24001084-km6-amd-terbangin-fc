package com.arkan.terbangin.data.source.network.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileResponse(
    @SerializedName("data")
    val data: ProfileData?,
    @SerializedName("message")
    val message: String?,
)
