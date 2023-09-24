package com.example.practice_tvshowapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.practice_tvshowapp.databinding.TvShowLayoutAdapterBinding
import com.example.practice_tvshowapp.models.tvshows.TvShowItem

class TvShowAdapter:RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    inner class TvShowViewHolder(val binding: TvShowLayoutAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<TvShowItem>() {
        override fun areItemsTheSame(oldItem: TvShowItem, newItem: TvShowItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShowItem, newItem: TvShowItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
        var tvShows: List<TvShowItem>
            get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
            TvShowLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val currentTvShow = tvShows[position]

        holder.binding.apply {
            tvShowTitle.text = currentTvShow.name
            tvShowImageView.load(currentTvShow.image?.original) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

}