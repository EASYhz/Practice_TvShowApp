package com.example.practice_tvshowapp.utils

import com.example.practice_tvshowapp.models.tvshows.Schedule
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class CommonUtilsTest {

//    @Test
//    fun getYesterdayDate() = runTest {
//
//    }
//
//    @Test
//    fun convertDpToPx() = runTest {
//
//    }

    @Test
    fun `getTvShowInfoDate case 1`() = runTest {
        val actualValue = CommonUtils.getTvShowInfoDate(premiered = "2023-01-03", ended = "2023-10-20")
        val expectedValue = "2023-01-03 ~ 2023-10-20"
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `getTvShowInfoDate case 2`() = runTest {
        val actualValue = CommonUtils.getTvShowInfoDate(premiered = "2023-01-03", ended = null)
        val expectedValue = "2023-01-03 ~ "
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `getTvShowInfoDate case 3`() = runTest {
        val actualValue = CommonUtils.getTvShowInfoDate(premiered = null, ended = "2023-01-03")
        val expectedValue = " ~ 2023-01-03"
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getTvShowInfoTime() = runTest {
        val mockValue = Schedule(days = listOf("Monday", "Friday", "Sunday"), time = "23:00")
        val expectedValue = "Mon,Fri,Sun 23:00"
        val actualValue = CommonUtils.getTvShowInfoTime(mockValue)

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getTvShowInfoGenres() = runTest {
        val mockValue = listOf("Drama", "Science-Fiction", "Thriller")
        val expectedValue = "Drama  |  Science-Fiction  |  Thriller"
        val actualValue = CommonUtils.getTvShowInfoGenres(mockValue)

        assertEquals(expectedValue, actualValue)
    }
}