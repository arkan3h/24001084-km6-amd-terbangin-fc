package com.arkan.terbangin.data.source.network.model.payment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("snapLink")
    val snapLink: String?,
    @SerializedName("snapToken")
    val snapToken: String?,
    @SerializedName("method")
    val method: String?,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
