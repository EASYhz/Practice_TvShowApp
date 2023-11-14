package com.example.practice_tvshowapp.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.practice_tvshowapp.database.TvShowItemDatabase
import com.example.practice_tvshowapp.models.tvshows.Image
import com.example.practice_tvshowapp.models.tvshows.Rating
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.rules.TestCoroutinesRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class TvShowItemDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var db : TvShowItemDatabase
    private lateinit var tvShowItemDao: TvShowItemDao

    @Before
    fun setUp() {
        // Create a Room database for Test
        hiltRule.inject()
        tvShowItemDao = db.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        // Database close
        db.close()
    }
//    @Test
//    fun useAppContext() = runTest {
//        println("23r23r32r")
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.practice_tvshowapp", appContext.packageName)
//    }
    @Test
    @Throws(Exception::class)
    fun insertTvShowItem() = runTest {
        val tvShowItem = TvShowItem(
            id = 1,
            averageRuntime = 60,
            ended = "2023-10-20",
            genres = listOf("A", "B", "C"),
            airdate = null,
            image = Image(medium = "TEST MEDIUM", original = "TEST ORIGINAL"),
            language = "English",
            name = "TEST",
            officialSite = "TEST",
            premiered = "2023-10-20",
            rating = Rating(filedId = 1, average = 10.0),
            runtime = 60,
            schedule = null,
            status = "end",
            summary = "This is..",
            type = "Movie",
            updated = 10,
            url = "TEST",
            weight = 10,
        )

        tvShowItemDao.insertTvShow(tvShowItem)
        val allTvShowItem = tvShowItemDao.getAll()
        println("--------------------")
        println(allTvShowItem)
        assertEquals(allTvShowItem[0].id, tvShowItem.id)
    }
}