package com.arkan.terbangin.data.source.network.model.detailh

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Payment(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("method")
    val method: Any?,
    @SerializedName("snapLink")
    val snapLink: String?,
    @SerializedName("snapToken")
    val snapToken: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalPrice")
    val totalPrice: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: String?,
)
