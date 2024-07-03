package com.arkan.terbangin.data.repository.searchhistory

import app.cash.turbine.test
import com.arkan.terbangin.data.datasource.searchHistory.SearchTerminalDataSource
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity
import com.arkan.terbangin.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchTerminalRepositoryImplTest {
    @MockK
    lateinit var dataSource: SearchTerminalDataSource

    private lateinit var repo: SearchTerminalRepository

    private val mockHistory1 = SearchTerminalEntity(1, "query1", System.currentTimeMillis())
    private val mockHistory2 = SearchTerminalEntity(2, "query2", System.currentTimeMillis())
    private val mockHistoryList = listOf(mockHistory1, mockHistory2)
    private val mockSearchHistory1 = SearchHistory(1, "query1", System.currentTimeMillis())
    private val mockSearchHistory2 = SearchHistory(2, "query2", System.currentTimeMillis())
    private val mockSearchHistoryList = listOf(mockSearchHistory1, mockSearchHistory2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = SearchTerminalRepositoryImpl(dataSource)
    }

    @Test
    fun insertSearchHistoryLoading() {
        val query = "testQuery"
        val existingEntry = mockHistory1

        runTest {
            coEvery { dataSource.getSearchHistoryByName(query) } returns existingEntry
            coEvery { dataSource.insertSearchHistory(any()) } returns 1
            repo.insertSearchHistory(query).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { dataSource.insertSearchHistory(any()) }
            }
        }
    }

    @Test
    fun insertSearchHistorySuccess() {
        val query = "testQuery"
        val existingEntry = mockHistory1

        runTest {
            coEvery { dataSource.getSearchHistoryByName(query) } returns existingEntry
            coEvery { dataSource.insertSearchHistory(any()) } returns 1
            repo.insertSearchHistory(query).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify(atLeast = 1) { dataSource.insertSearchHistory(any()) }
            }
        }
    }

    @Test
    fun insertSearchHistoryError() {
        val query = "testQuery"
        val existingEntry = mockHistory1

        runTest {
            coEvery { dataSource.getSearchHistoryByName(query) } returns existingEntry
            coEvery { dataSource.insertSearchHistory(any()) } throws IllegalStateException("Mock Error")
            repo.insertSearchHistory(query).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { dataSource.insertSearchHistory(any()) }
            }
        }
    }

    @Test
    fun insertSearchHistoryNewEntryLoading() {
        val query = "newQuery"

        runTest {
            coEvery { dataSource.getSearchHistoryByName(query) } returns null
            coEvery { dataSource.insertSearchHistory(any()) } returns 1

            repo.insertSearchHistory(query).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { dataSource.insertSearchHistory(any()) }
            }
        }
    }

    @Test
    fun insertSearchHistoryNewEntrySuccess() {
        val query = "newQuery"

        runTest {
            coEvery { dataSource.getSearchHistoryByName(query) } returns null
            coEvery { dataSource.insertSearchHistory(any()) } returns 1

            repo.insertSearchHistory(query).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify(atLeast = 1) { dataSource.insertSearchHistory(any()) }
            }
        }
    }

    @Test
    fun insertSearchHistoryNewEntryError() {
        val query = "newQuery"

        runTest {
            coEvery { dataSource.getSearchHistoryByName(query) } returns null
            coEvery { dataSource.insertSearchHistory(any()) } throws IllegalStateException("Mock Error")

            repo.insertSearchHistory(query).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { dataSource.insertSearchHistory(any()) }
            }
        }
    }

    @Test
    fun getSearchHistoryLoading() {
        val mockFlow = flow { emit(mockHistoryList) }

        every { dataSource.getSearchHistory() } returns mockFlow

        runTest {
            repo.getSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.getSearchHistory() }
            }
        }
    }

    @Test
    fun getSearchHistorySuccess() {
        val mockFlow = flow { emit(mockHistoryList) }

        every { dataSource.getSearchHistory() } returns mockFlow

        runTest {
            repo.getSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockSearchHistoryList.size, actualData.payload?.size)
                coVerify { dataSource.getSearchHistory() }
            }
        }
    }

    @Test
    fun getSearchHistoryEmpty() {
        val mockHistoryList = listOf<SearchTerminalEntity>()
        val mockFlow = flow { emit(mockHistoryList) }

        every { dataSource.getSearchHistory() } returns mockFlow

        runTest {
            repo.getSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                coVerify { dataSource.getSearchHistory() }
            }
        }
    }

    @Test
    fun getSearchHistoryError() {
        every { dataSource.getSearchHistory() } returns
            flow {
                throw IllegalStateException("Mock Error")
            }

        runTest {
            repo.getSearchHistory().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.getSearchHistory() }
            }
        }
    }

    @Test
    fun deleteSearchHistoryLoading() {
        runTest {
            coEvery { dataSource.deleteSearchHistory(any()) } returns 1

            repo.deleteSearchHistory(mockSearchHistory1).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { dataSource.deleteSearchHistory(any()) }
            }
        }
    }

    @Test
    fun deleteSearchHistorySuccess() {
        runTest {
            coEvery { dataSource.deleteSearchHistory(any()) } returns 1

            repo.deleteSearchHistory(mockSearchHistory1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteSearchHistory(any()) }
            }
        }
    }

    @Test
    fun deleteSearchHistoryError() {
        coEvery { dataSource.deleteSearchHistory(any()) } throws IllegalStateException("Mock Error")

        runTest {
            repo.deleteSearchHistory(mockSearchHistory1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { dataSource.deleteSearchHistory(any()) }
            }
        }
    }

    @Test
    fun clearSearchHistorySuccess() {
        runTest {
            coEvery { repo.clearSearchHistory() } returns Unit

            val result = dataSource.clearSearchHistory()
            coVerify {
                repo.clearSearchHistory()
                assertEquals(Unit, result)
            }
        }
    }
}
