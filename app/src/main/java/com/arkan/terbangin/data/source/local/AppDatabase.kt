package com.arkan.terbangin.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arkan.terbangin.data.source.local.dao.SearchHistoryDao
import com.arkan.terbangin.data.source.local.dao.SearchTerminalDao
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity

@Database(
    entities = [SearchHistoryEntity::class, SearchTerminalEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    abstract fun searchTerminalDao(): SearchTerminalDao

    companion object {
        private const val DB_NAME = "Terbangin.db"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration().build()
        }
    }
}
