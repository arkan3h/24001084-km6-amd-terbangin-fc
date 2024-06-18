package com.arkan.terbangin.data.datasource.searchHistory

import com.arkan.terbangin.data.source.local.dao.SearchTerminalDao
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity
import kotlinx.coroutines.flow.Flow

class SearchTerminalDatabaseDataSource(
    private val dao: SearchTerminalDao,
) : SearchTerminalDataSource {
    override suspend fun insertSearchHistory(searchTerminalEntity: SearchTerminalEntity): Long =
        dao.insertSearchHistory(searchTerminalEntity)

    override suspend fun getSearchHistoryByName(query: String): SearchTerminalEntity? = dao.getSearchHistoryByName(query)

    override fun getSearchHistory(): Flow<List<SearchTerminalEntity>> = dao.getSearchHistory()

    override suspend fun deleteSearchHistory(searchTerminalEntity: SearchTerminalEntity): Int =
        dao.deleteSearchHistory(
            searchTerminalEntity,
        )

    override suspend fun clearSearchHistory() = dao.clearSearchHistory()
}
