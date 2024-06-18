package com.arkan.terbangin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity): Long

    @Query("SELECT * FROM search_history WHERE `query` = :query LIMIT 1")
    suspend fun getSearchHistoryByName(query: String): SearchHistoryEntity?

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Delete
    suspend fun deleteSearchHistory(searchHistoryEntity: SearchHistoryEntity): Int

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()
}
