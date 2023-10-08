package com.example.practice_tvshowapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_tvshowapp.databinding.TvShowContainerLayoutAdapterBinding
import com.example.practice_tvshowapp.models.tvshows.TvShowContainer
import com.example.practice_tvshowapp.models.tvshows.TvShowItem

class TvShowContainerAdapter(
    private val onClickListener: (TvShowItem) -> Unit
) : RecyclerView.Adapter<TvShowContainerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: TvShowContainerLayoutAdapterBinding) :
            RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<TvShowContainer>() {
        override fun areItemsTheSame(oldItem: TvShowContainer, newItem: TvShowContainer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TvShowContainer,
            newItem: TvShowContainer
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var tvShowContainers: List<TvShowContainer>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            TvShowContainerLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /* binding */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tvShowAdapter = TvShowAdapter(onItemClickListener = onClickListener)
        val currentTvShowContainer = tvShowContainers[position]
        holder.binding.apply {
            subjectTextView.text = currentTvShowContainer.subject
            tvShowAdapter.tvShows = currentTvShowContainer.tvShows
            tvRecyclerView.apply {
                adapter = tvShowAdapter
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }
        }
    }

    override fun getItemCount(): Int = tvShowContainers.size


}