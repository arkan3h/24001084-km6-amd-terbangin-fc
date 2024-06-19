package com.arkan.terbangin.data.repository.notification

import com.arkan.terbangin.data.datasource.notification.NotificationDataSource
import com.arkan.terbangin.data.mapper.toListNotification
import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationByUID(id: String): Flow<ResultWrapper<List<Notification>>>
}

class NotificationRepositoryImpl(
    private val dataSource: NotificationDataSource,
) : NotificationRepository {
    override fun getNotificationByUID(id: String): Flow<ResultWrapper<List<Notification>>> {
        return proceedFlow {
            dataSource.getNotificationByUID(id).data.toListNotification()
        }
    }
}
