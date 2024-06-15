package com.arkan.terbangin.data.datasource.searchHistory

import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface SearchHistoryDataSource {
    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity): Long

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    suspend fun deleteSearchHistory(searchHistoryEntity: SearchHistoryEntity): Int

    suspend fun clearSearchHistory()
}
