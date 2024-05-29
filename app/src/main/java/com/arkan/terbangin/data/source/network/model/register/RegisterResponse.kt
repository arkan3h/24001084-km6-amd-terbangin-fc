package com.arkan.terbangin.data.source.network.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: RegisterData?,
)
