package com.arkan.terbangin.data.datasource.searchHistory

import app.cash.turbine.test
import com.arkan.terbangin.data.source.local.dao.SearchTerminalDao
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity
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

class SearchTerminalDatabaseDataSourceTest {
    @MockK
    lateinit var searchTerminalDao: SearchTerminalDao

    private lateinit var searchTerminalDataSource: SearchTerminalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchTerminalDataSource = SearchTerminalDatabaseDataSource(searchTerminalDao)
    }

    @Test
    fun insertSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchTerminalEntity>()
            coEvery {
                searchTerminalDao.insertSearchHistory(any())
            } returns 1

            val result = searchTerminalDataSource.insertSearchHistory(mockEntity)
            coVerify {
                searchTerminalDao.insertSearchHistory(any())
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun getSearchHistoryByName_existingName_returnsEntity() {
        runTest {
            val query = "exampleQuery"
            val mockEntity = mockk<SearchTerminalEntity>()

            coEvery {
                searchTerminalDao.getSearchHistoryByName(query)
            } returns mockEntity

            val result = searchTerminalDataSource.getSearchHistoryByName(query)
            assertEquals(mockEntity, result)

            coVerify {
                searchTerminalDao.getSearchHistoryByName(query)
            }
        }
    }

    @Test
    fun getSearchHistoryByName_nonExistingName_returnsNull() {
        runTest {
            val query = "nonExistingQuery"

            coEvery {
                searchTerminalDao.getSearchHistoryByName(query)
            } returns null

            val result = searchTerminalDataSource.getSearchHistoryByName(query)
            assertEquals(null, result)

            coVerify {
                searchTerminalDao.getSearchHistoryByName(query)
            }
        }
    }

    @Test
    fun getSearchHistory() {
        runTest {
            val entity1 = mockk<SearchTerminalEntity>()
            val entity2 = mockk<SearchTerminalEntity>()
            val listEntity = listOf(entity1, entity2)
            val mockFlow =
                flow {
                    emit(listEntity)
                }

            coEvery {
                searchTerminalDao.getSearchHistory()
            } returns mockFlow

            searchTerminalDataSource.getSearchHistory().test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()

                coVerify {
                    searchTerminalDao.getSearchHistory()
                }
            }
        }
    }

    @Test
    fun deleteSearchHistory() {
        runTest {
            val mockEntity = mockk<SearchTerminalEntity>()
            coEvery {
                searchTerminalDao.deleteSearchHistory(any())
            } returns 1

            val result = searchTerminalDataSource.deleteSearchHistory(mockEntity)
            coVerify {
                searchTerminalDao.deleteSearchHistory(any())
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun clearSearchHistory() {
        runTest {
            coEvery {
                searchTerminalDao.clearSearchHistory()
            } returns Unit

            val result = searchTerminalDataSource.clearSearchHistory()
            coVerify {
                searchTerminalDao.clearSearchHistory()
                assertEquals(Unit, result)
            }
        }
    }
}
