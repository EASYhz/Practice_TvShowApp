package com.example.practice_tvshowapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.adapter.EpisodeAdapter
import com.example.practice_tvshowapp.databinding.EpisodeContainerLayoutBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel
import com.example.practice_tvshowapp.views.EpisodesActivity

class EpisodesFragment : Fragment() {
    lateinit var binding: EpisodeContainerLayoutBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var viewModel: EpisodesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodeContainerLayoutBinding.inflate(inflater, container, false)
        episodeAdapter = EpisodeAdapter()
        viewModel = ViewModelProvider(activity as EpisodesActivity)[EpisodesViewModel::class.java]

        observeEpisode()
        setEpisodesView()

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun observeEpisode() {
        viewModel.tvShowEpisodeResponse.observe(viewLifecycleOwner) { episodes ->
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