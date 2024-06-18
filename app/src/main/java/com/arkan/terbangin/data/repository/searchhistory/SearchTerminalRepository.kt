package com.arkan.terbangin.data.repository.searchhistory

import com.arkan.terbangin.data.datasource.searchHistory.SearchTerminalDataSource
import com.arkan.terbangin.data.mapper.toSearchTerminalEntity
import com.arkan.terbangin.data.mapper.toSearchTerminalList
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceed
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface SearchTerminalRepository {
    fun insertSearchHistory(query: String): Flow<ResultWrapper<Boolean>>

    fun getSearchHistory(): Flow<ResultWrapper<List<SearchHistory>>>

    fun deleteSearchHistory(searchHistory: SearchHistory): Flow<ResultWrapper<Boolean>>

    suspend fun clearSearchHistory()
}

class SearchTerminalRepositoryImpl(
    private val dataSource: SearchTerminalDataSource,
) : SearchTerminalRepository {
    override fun insertSearchHistory(query: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val existingEntry = dataSource.getSearchHistoryByName(query)
            val row =
                if (existingEntry != null) {
                    val updatedEntry = existingEntry.copy(timestamp = System.currentTimeMillis())
                    dataSource.insertSearchHistory(updatedEntry)
                } else {
                    dataSource.insertSearchHistory(
                        SearchTerminalEntity(
                            query = query,
                            timestamp = System.currentTimeMillis(),
                        ),
                    )
                }
            row > 0
        }
    }

    override fun getSearchHistory(): Flow<ResultWrapper<List<SearchHistory>>> {
        return dataSource.getSearchHistory()
            .map {
                proceed {
                    val searchHistory = it.toSearchTerminalList()
                    searchHistory
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.catch {
                emit(ResultWrapper.Error(Exception(it)))
            }.onStart {
                emit(ResultWrapper.Loading())
            }
    }

    override fun deleteSearchHistory(searchHistory: SearchHistory): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.deleteSearchHistory(searchHistory.toSearchTerminalEntity()) > 0
        }
    }

    override suspend fun clearSearchHistory() {
        return dataSource.clearSearchHistory()
    }
}
