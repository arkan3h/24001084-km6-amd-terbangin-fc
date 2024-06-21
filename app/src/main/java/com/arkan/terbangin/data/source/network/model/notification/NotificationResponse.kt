package com.arkan.terbangin.data.source.network.model.notification

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationResponse(
    @SerializedName("data")
    val data: List<NotificationData>?,
    @SerializedName("message")
    val message: String?,
)
