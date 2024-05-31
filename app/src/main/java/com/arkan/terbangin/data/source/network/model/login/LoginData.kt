package com.arkan.terbangin.data.source.network.model.login

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("user")
    val user: LoginUser?,
    @SerializedName("token")
    val token: String?,
)
