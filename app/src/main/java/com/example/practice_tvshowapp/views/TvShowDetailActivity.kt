package com.example.practice_tvshowapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.practice_tvshowapp.R
import com.example.practice_tvshowapp.databinding.ActivityTvShowDetailBinding
import com.example.practice_tvshowapp.viewmodel.TvShowEpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailBinding
    private lateinit var viewModel: TvShowEpisodesViewModel
    @Inject lateinit var tvShowEpisodesViewModelFactory: TvShowEpisodesViewModel.TvShowEpisodesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tvShowDetailIntent = intent
        val tvShowId = tvShowDetailIntent.getIntExtra("tvShowId", 0)

        viewModel = viewModels<TvShowEpisodesViewModel> {
            TvShowEpisodesViewModel.provideFactory(tvShowEpisodesViewModelFactory, tvShowId)
        }.value

        observeTvShowEpisode()
    }

    private fun observeTvShowEpisode() {
        viewModel.tvShowEpisodeResponse.observe(this) { episodes ->

        }
    }
}