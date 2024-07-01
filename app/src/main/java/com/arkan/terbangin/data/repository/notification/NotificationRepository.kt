package com.arkan.terbangin.data.repository.notification

import com.arkan.terbangin.data.datasource.notification.NotificationDataSource
import com.arkan.terbangin.data.mapper.toListNotification
import com.arkan.terbangin.data.mapper.toNotification
import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationByUID(id: String): Flow<ResultWrapper<List<Notification>>>

    fun readNotification(id: String): Flow<ResultWrapper<Notification>>
}

class NotificationRepositoryImpl(
    private val dataSource: NotificationDataSource,
) : NotificationRepository {
    override fun getNotificationByUID(id: String): Flow<ResultWrapper<List<Notification>>> {
        return proceedFlow {
            dataSource.getNotificationByUID(id).data.toListNotification()
        }
    }

    override fun readNotification(id: String): Flow<ResultWrapper<Notification>> {
        return proceedFlow {
            dataSource.readNotification(id).data.toNotification()
        }
    }
}
