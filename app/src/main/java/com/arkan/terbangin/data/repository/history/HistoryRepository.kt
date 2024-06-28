package com.arkan.terbangin.data.repository.history

import com.arkan.terbangin.data.datasource.history.HistoryDataSource
import com.arkan.terbangin.data.mapper.toHistoryList
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryData(id: String): Flow<ResultWrapper<List<History>>>
}

class HistoryRepositoryImpl(private val dataSource: HistoryDataSource) : HistoryRepository {
    override fun getHistoryData(id: String): Flow<ResultWrapper<List<History>>> {
        return proceedFlow {
            dataSource.getHistoryData(id).data.toHistoryList()
        }
    }
}
