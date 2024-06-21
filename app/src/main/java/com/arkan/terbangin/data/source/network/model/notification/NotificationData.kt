package com.arkan.terbangin.data.source.network.model.notification

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("bookingId")
    val bookingId: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusRead")
    val statusRead: Boolean?,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
)
