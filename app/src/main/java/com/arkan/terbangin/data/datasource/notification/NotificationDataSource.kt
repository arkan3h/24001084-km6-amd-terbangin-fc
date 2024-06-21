package com.arkan.terbangin.data.datasource.notification

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.notification.NotificationData

interface NotificationDataSource {
    suspend fun getNotificationByUID(id: String): Response<List<NotificationData>?>
}
