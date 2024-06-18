package com.arkan.terbangin.data.datasource.searchHistory

import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity
import kotlinx.coroutines.flow.Flow

interface SearchTerminalDataSource {
    suspend fun insertSearchHistory(searchTerminalEntity: SearchTerminalEntity): Long

    suspend fun getSearchHistoryByName(query: String): SearchTerminalEntity?

    fun getSearchHistory(): Flow<List<SearchTerminalEntity>>

    suspend fun deleteSearchHistory(searchTerminalEntity: SearchTerminalEntity): Int

    suspend fun clearSearchHistory()
}
