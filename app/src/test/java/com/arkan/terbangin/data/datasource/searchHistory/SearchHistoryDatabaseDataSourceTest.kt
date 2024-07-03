package com.arkan.terbangin.data.datasource.searchHistory

import app.cash.turbine.test
import com.arkan.terbangin.data.source.local.dao.SearchHistoryDao
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchHistoryDatabaseDataSourceTest {
    @MockK
    lateinit var searchHistoryDao: SearchHistoryDao

    private lateinit var searchHistoryDataSource: SearchHistoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchHistoryDataSource = SearchHistoryDatabaseDataSource(searchHistoryDao)
    }

    @Test
    fun insertSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchHistoryEntity>()
            coEvery { searchHistoryDao.insertSearchHistory(any()) } returns 1

            val result = searchHistoryDataSource.insertSearchHistory(mockEntity)

            coVerify {
                searchHistoryDao.insertSearchHistory(mockEntity)
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun getSearchHistoryByName_existingName_returnsEntity() {
        runTest {
            val query = "exampleQuery"
            val mockEntity = mockk<SearchHistoryEntity>()

            coEvery {
                searchHistoryDao.getSearchHistoryByName(query)
            } returns mockEntity

            val result = searchHistoryDataSource.getSearchHistoryByName(query)
            assertEquals(mockEntity, result)

            coVerify {
                searchHistoryDao.getSearchHistoryByName(query)
            }
        }
    }

    @Test
    fun getSearchHistoryByName_nonExistingName_returnsNull() {
        runTest {
            val query = "nonExistingQuery"

            coEvery {
                searchHistoryDao.getSearchHistoryByName(query)
            } returns null

            val result = searchHistoryDataSource.getSearchHistoryByName(query)
            assertEquals(null, result)

            coVerify {
                searchHistoryDao.getSearchHistoryByName(query)
            }
        }
    }

    @Test
    fun getSearchHistory() {
        runTest {
            val entity1 = mockk<SearchHistoryEntity>()
            val entity2 = mockk<SearchHistoryEntity>()
            val listEntity = listOf(entity1, entity2)
            val mockFlow =
                flow {
                    emit(listEntity)
                }

            coEvery {
                searchHistoryDao.getSearchHistory()
            } returns mockFlow

            searchHistoryDataSource.getSearchHistory().test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()

                coVerify {
                    searchHistoryDao.getSearchHistory()
                }
            }
        }
    }

    @Test
    fun deleteSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchHistoryEntity>()
            coEvery { searchHistoryDao.deleteSearchHistory(any()) } returns 1

            val result = searchHistoryDataSource.deleteSearchHistory(mockEntity)

            coVerify {
                searchHistoryDao.deleteSearchHistory(mockEntity)
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun clearSearchHistory() {
        runTest {
            coEvery { searchHistoryDao.clearSearchHistory() } returns Unit

            val result = searchHistoryDataSource.clearSearchHistory()

            coVerify {
                searchHistoryDao.clearSearchHistory()
                assertEquals(Unit, result)
            }
        }
    }
}
