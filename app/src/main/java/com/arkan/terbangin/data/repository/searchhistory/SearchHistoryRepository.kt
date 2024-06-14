package com.arkan.terbangin.data.repository.searchhistory

import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.source.data.SearchHistoryDao

class SearchHistoryRepository(private val searchHistoryDao: SearchHistoryDao) {
    suspend fun insertSearchHistory(query: String) {
        val searchHistory = SearchHistory(query = query)
        searchHistoryDao.insertSearchHistory(searchHistory)
    }

    suspend fun getSearchHistory(): List<SearchHistory> {
        return searchHistoryDao.getSearchHistory()
    }

    suspend fun clearSearchHistory() {
        searchHistoryDao.clearSearchHistory()
    }
}
