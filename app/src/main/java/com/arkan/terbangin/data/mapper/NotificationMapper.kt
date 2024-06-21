package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.data.source.network.model.notification.NotificationData

fun NotificationData?.toNotification() =
    Notification(
        id = this?.id.orEmpty(),
        userId = this?.userId.orEmpty(),
        bookingId = this?.bookingId.orEmpty(),
        title = this?.title.orEmpty(),
        message = this?.message.orEmpty(),
        statusRead = this?.statusRead ?: false,
        createdAt = this?.createdAt.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        deletedAt = this?.deletedAt.orEmpty(),
    )

fun Collection<NotificationData>?.toListNotification() = this?.map { it.toNotification() } ?: listOf()
