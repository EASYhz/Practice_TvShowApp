package com.example.practice_tvshowapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.adapter.EpisodeAdapter
import com.example.practice_tvshowapp.databinding.EpisodeContainerLayoutBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel

class EpisodesFragment : Fragment() {
    lateinit var binding: EpisodeContainerLayoutBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private val viewModel: EpisodesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodeContainerLayoutBinding.inflate(inflater, container, false)
        episodeAdapter = EpisodeAdapter()
        observeEpisode()

        return binding.root
    }

    override fun onResume() {
        setEpisodesView()
        super.onResume()
    }


    private fun observeEpisode() {
        viewModel.tvShowEpisodeResponse.observe(this) { episodes ->
            episodeAdapter.episodes = episodes
        }
    }


    private fun setEpisodesView() {
        binding.episodesRecyclerView.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

}