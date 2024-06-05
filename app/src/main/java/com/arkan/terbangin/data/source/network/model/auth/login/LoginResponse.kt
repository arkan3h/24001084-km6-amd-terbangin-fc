package com.arkan.terbangin.data.source.network.model.auth.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: LoginData?,
)
