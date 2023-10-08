package com.example.practice_tvshowapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.practice_tvshowapp.databinding.CastLayoutAdapterBinding
import com.example.practice_tvshowapp.models.casts.CastItem

class CastAdapter : RecyclerView.Adapter<CastAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: CastLayoutAdapterBinding) :
            RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<CastItem>() {
        override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
            return oldItem.person.id == newItem.person.id
        }

        override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var casts: List<CastItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CastLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCast = casts[position]

        holder.binding.apply {
            personName.text = currentCast.person.name
            characterName.text = currentCast.character.name
            val image = currentCast.character.image?.original ?: currentCast.person.image.original

            characterImage.load(image) {
                crossfade(true)
                crossfade(1000)
            }

        }
    }

    override fun getItemCount(): Int = casts.size
}