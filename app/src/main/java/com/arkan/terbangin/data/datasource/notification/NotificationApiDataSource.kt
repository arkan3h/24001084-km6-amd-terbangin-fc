package com.arkan.terbangin.data.datasource.notification

import com.arkan.terbangin.data.source.network.model.notification.NotificationResponse
import com.arkan.terbangin.data.source.network.services.TerbanginApiServices

class NotificationApiDataSource(
    private val services: TerbanginApiServices,
) : NotificationDataSource {
    override suspend fun getNotificationByUID(id: String): NotificationResponse {
        return services.getNotificationByUID(id)
    }
}
