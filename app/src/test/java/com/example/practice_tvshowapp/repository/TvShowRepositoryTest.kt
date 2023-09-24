package com.example.practice_tvshowapp.repository

import com.example.practice_tvshowapp.api.TvShowService
import com.example.practice_tvshowapp.di.ApiModule
import com.example.practice_tvshowapp.utils.CommonUtils.getYesterdayDate
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.text.SimpleDateFormat
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TvShowRepositoryTest {
    private lateinit var repository: TvShowRepository
    private lateinit var tvShowService: TvShowService

    @BeforeAll
    fun setUp() {
        tvShowService = ApiModule.tvShowService()
        repository = TvShowRepository(tvShowService)
    }

    @Test
    fun `Get Tv Show Api Test`() = runTest {
        val mockTvShow = listOf(
//            TvShowItem(
//                id = 1,
//                url = "https://www.tvmaze.com/shows/1/under-the-dome",
//                name = "Under the Dome"
//            ), TvShowItem(
//                id=2,
//                url = "https://www.tvmaze.com/shows/2/person-of-interest",
//                name = "Person of Interest"
//            )
            1, 2
        )
        val actualResponse = repository.getAllTvShows()
        val tvShow = listOf(actualResponse.body()?.get(0)?.id, actualResponse.body()?.get(1)?.id)
        assertEquals(mockTvShow, tvShow)
    }

    @Test
    fun `Get Tv Show on Yesterday Api Test`() = runTest {
        val finalResultDate = getYesterdayDate()
        val mockValue = listOf(
            finalResultDate, finalResultDate
        )

        val actualResponse = repository.getWebTvShowOnYesterday(finalResultDate)
        val tvShow = listOf(actualResponse.body()?.get(0)?.airdate, actualResponse.body()?.get(1)?.airdate)
        assertEquals(mockValue, tvShow)
    }
}