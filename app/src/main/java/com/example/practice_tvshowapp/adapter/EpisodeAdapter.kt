package com.example.practice_tvshowapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.practice_tvshowapp.databinding.EpisodeLayoutAdapterBinding
import com.example.practice_tvshowapp.models.episodes.EpisodeItem

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: EpisodeLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<EpisodeItem>() {
        override fun areItemsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeItem, newItem: EpisodeItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var episodes : List<EpisodeItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            EpisodeLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEpisode = episodes[position]

        holder.binding.apply {
            episodeTitleTextView.text = setEpisodeTitle(currentEpisode.season, currentEpisode.number, currentEpisode.name)
            episodeTimeTextView.text = setEpisodeRunTime(currentEpisode.runtime)
            episodeSummaryTextView.text = HtmlCompat.fromHtml(currentEpisode.summary, FROM_HTML_MODE_LEGACY)
            episodeImageView.load(currentEpisode.image.original) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount(): Int = episodes.size

    private fun setEpisodeTitle(season:Int, number: Int, title: String) = "$season-$number. $title"

    private fun setEpisodeRunTime(runTime: Int) = "$runTime min"
}