package com.arkan.terbangin.data.datasource.notification

import com.arkan.terbangin.data.source.network.model.notification.NotificationResponse

interface NotificationDataSource {
    suspend fun getNotificationByUID(id: String): NotificationResponse
}
