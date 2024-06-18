package com.arkan.terbangin.data.datasource.searchHistory

import com.arkan.terbangin.data.source.local.dao.SearchHistoryDao
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

class SearchHistoryDatabaseDataSource(
    private val dao: SearchHistoryDao,
) : SearchHistoryDataSource {
    override suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity): Long = dao.insertSearchHistory(searchHistoryEntity)

    override suspend fun getSearchHistoryByName(query: String): SearchHistoryEntity? = dao.getSearchHistoryByName(query)

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> = dao.getSearchHistory()

    override suspend fun deleteSearchHistory(searchHistoryEntity: SearchHistoryEntity): Int = dao.deleteSearchHistory(searchHistoryEntity)

    override suspend fun clearSearchHistory() = dao.clearSearchHistory()
}
