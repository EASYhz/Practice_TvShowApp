package com.example.practice_tvshowapp.adapter.bindingAdapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.practice_tvshowapp.models.tvshows.Schedule
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoDate
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoGenres
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoTime

object EpisodesBindingAdapter {
    @JvmStatic
    @BindingAdapter("image_src")
    fun setImage(view: ImageView, url: String) {
        view.load(url) {
            crossfade(true)
            crossfade(1000)
        }
    }

    @JvmStatic
    @BindingAdapter("summary_text")
    fun setSummary(view: TextView, summary: String) {
        view.text = HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    @JvmStatic
    @BindingAdapter("premiered", "ended")
    fun setInfoDate(view: TextView, premiered: String, ended: String) {
        view.text = getTvShowInfoDate(premiered, ended)
    }

    @JvmStatic
    @BindingAdapter("time_text")
    fun setInfoTime(view: TextView, schedule: Schedule) {
        view.text = getTvShowInfoTime(schedule)
    }

    @JvmStatic
    @BindingAdapter("genre_text")
    fun setInfoGenres(view: TextView, genres: List<String>) {
        view.text = getTvShowInfoGenres(genres)
    }
}