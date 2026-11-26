package com.example.mobileproject.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: BookDao

    private val dune = BookEntity(
        id = "OL1W", title = "Dune", author = "Frank Herbert", description = "",
        status = ReadingStatus.WANT, coverUrl = null, year = 1965
    )

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.bookDao()
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun insert_thenRead() = runTest {
        dao.insert(dune)
        assertEquals(listOf(dune), dao.observeAll().first())
    }

    @Test
    fun update_changesStatus() = runTest {
        dao.insert(dune)
        dao.update(dune.copy(status = ReadingStatus.DONE))
        assertEquals(ReadingStatus.DONE, dao.getById("OL1W")?.status)
    }

    @Test
    fun delete_removesBook() = runTest {
        dao.insert(dune)
        dao.deleteById("OL1W")
        assertNull(dao.getById("OL1W"))
    }
}