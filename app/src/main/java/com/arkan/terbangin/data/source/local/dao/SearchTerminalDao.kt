package com.arkan.terbangin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchTerminalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchTerminalEntity: SearchTerminalEntity): Long

    @Query("SELECT * FROM search_terminal WHERE `query` = :query LIMIT 1")
    suspend fun getSearchHistoryByName(query: String): SearchTerminalEntity?

    @Query("SELECT * FROM search_terminal ORDER BY timestamp DESC")
    fun getSearchHistory(): Flow<List<SearchTerminalEntity>>

    @Delete
    suspend fun deleteSearchHistory(searchTerminalEntity: SearchTerminalEntity): Int

    @Query("DELETE FROM search_terminal")
    suspend fun clearSearchHistory()
}
