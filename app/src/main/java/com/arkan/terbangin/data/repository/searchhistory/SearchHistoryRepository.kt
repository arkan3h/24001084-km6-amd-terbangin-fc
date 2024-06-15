package com.arkan.terbangin.data.repository.searchhistory

import com.arkan.terbangin.data.datasource.searchHistory.SearchHistoryDataSource
import com.arkan.terbangin.data.mapper.toSearchHistoryEntity
import com.arkan.terbangin.data.mapper.toSearchHistoryList
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceed
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface SearchHistoryRepository {
    fun insertSearchHistory(query: String): Flow<ResultWrapper<Boolean>>

    fun getSearchHistory(): Flow<ResultWrapper<List<SearchHistory>>>

    fun deleteSearchHistory(searchHistory: SearchHistory): Flow<ResultWrapper<Boolean>>

    suspend fun clearSearchHistory()
}

class SearchHistoryRepositoryImpl(
    private val dataSource: SearchHistoryDataSource,
) : SearchHistoryRepository {
    override fun insertSearchHistory(query: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val row =
                dataSource.insertSearchHistory(
                    SearchHistoryEntity(
                        query = query,
                    ),
                )
            row > 0
        }
    }

    override fun getSearchHistory(): Flow<ResultWrapper<List<SearchHistory>>> {
        return dataSource.getSearchHistory()
            .map {
                proceed {
                    val searchHistory = it.toSearchHistoryList()
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
            dataSource.deleteSearchHistory(searchHistory.toSearchHistoryEntity()) > 0
        }
    }

    override suspend fun clearSearchHistory() {
        return dataSource.clearSearchHistory()
    }
}
