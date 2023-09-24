package com.example.practice_tvshowapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {
    private const val DATE_FORMAT = "yyyy-MM-dd"
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat(DATE_FORMAT)

    fun getYesterdayDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val timeToDate = calendar.time

        return formatter.format(timeToDate)
    }
}