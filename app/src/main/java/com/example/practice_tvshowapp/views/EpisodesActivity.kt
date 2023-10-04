package com.example.practice_tvshowapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.adapter.EpisodeAdapter
import com.example.practice_tvshowapp.databinding.ActivityEpisodesBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel
import com.example.practice_tvshowapp.factory.EpisodesViewModelFactory
import com.example.practice_tvshowapp.utils.LoadingUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEpisodesBinding
    private lateinit var viewModel: EpisodesViewModel
    @Inject lateinit var episodesViewModelFactory: EpisodesViewModelFactory.ViewModelFactory
    private lateinit var episodeAdapter: EpisodeAdapter
    private var loadingUtils: LoadingUtils = LoadingUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val episodesIntent = intent
        val tvShowId = episodesIntent.getIntExtra("tvShowId", 0)

        viewModel = viewModels<EpisodesViewModel> {
            EpisodesViewModelFactory.provideFactory(episodesViewModelFactory, tvShowId)
        }.value

        setUp()
    }


    private fun setUp() {
        setEpisodesView()
        observeEpisode()
        subscribeToIsLoadingState()
    }

    private fun observeEpisode() {
        viewModel.tvShowEpisodeResponse.observe(this) { episodes ->
            episodeAdapter.episodes = episodes
        }
    }

    private fun setEpisodesView() {
        episodeAdapter = EpisodeAdapter()

        binding.episodesRecyclerView.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(
                this@EpisodesActivity, LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    private fun subscribeToIsLoadingState() {
        lifecycleScope.launch {
            viewModel.isLoadingState.collectLatest { isLoading ->
                loadingUtils.setLoadingView(
                    loadingView = binding.episodeSkeletonLoadingView,
                    mainView = binding.episodesRecyclerView,
                    isLoading = isLoading
                )
            }
        }
    }
}