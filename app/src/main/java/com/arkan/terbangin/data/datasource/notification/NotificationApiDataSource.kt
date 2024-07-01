package com.arkan.terbangin.data.datasource.notification

import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.notification.NotificationData
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class NotificationApiDataSource(
    private val services: TerbanginApiServices,
) : NotificationDataSource {
    override suspend fun getNotificationByUID(id: String): Response<List<NotificationData>?> {
        return services.getNotificationByUID(id)
    }

    override suspend fun readNotification(id: String): Response<NotificationData?> {
        return services.readNotification(id)
    }
}
