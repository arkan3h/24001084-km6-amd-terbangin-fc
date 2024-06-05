package com.arkan.terbangin.data.source.network.model.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("user")
    val user: RegisterUser?,
    @SerializedName("token")
    val token: String?,
)
