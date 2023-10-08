package com.example.practice_tvshowapp.views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.practice_tvshowapp.adapter.EpisodeAdapter
import com.example.practice_tvshowapp.databinding.ActivityEpisodesBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel
import com.example.practice_tvshowapp.factory.EpisodesViewModelFactory
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoDate
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoGenres
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoTime
import com.example.practice_tvshowapp.utils.LoadingUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEpisodesBinding
    private lateinit var viewModel: EpisodesViewModel
    @Inject lateinit var episodesViewModelFactory: EpisodesViewModelFactory.ViewModelFactory
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var tvShowItem: TvShowItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val episodesIntent = intent
        tvShowItem = episodesIntent.serializable("tvShowItem")!!

        viewModel = viewModels<EpisodesViewModel> {
            EpisodesViewModelFactory.provideFactory(episodesViewModelFactory, tvShowItem.id)
        }.value

        setUp()
    }

    private fun setUp() {
        setTvShowInfo()
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

    private fun setTvShowInfo() {
        binding.infoLayout.apply {
            /* tvShow Info 가져오기 */
            tvShowInfoTitle.text = tvShowItem.name
            tvShowInfoDate.text = getTvShowInfoDate(tvShowItem.premiered, tvShowItem.ended)
            tvShowInfoTime.text = getTvShowInfoTime(tvShowItem.schedule)
            tvShowInfoGenres.text = getTvShowInfoGenres(tvShowItem.genres)
            tvShowInfoRating.text = tvShowItem.rating.average.toString()
            tvShowImageView.load(tvShowItem.image?.original) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    private fun subscribeToIsLoadingState() {
        lifecycleScope.launch {
            viewModel.isLoadingState.collectLatest { isLoading ->
                LoadingUtils.setLoadingView(
                    loadingView = binding.episodeSkeletonLoadingView,
                    mainView = binding.episodeContainerLayout,
                    isLoading = isLoading
                )
            }
        }
    }


    inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }
}