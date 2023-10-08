package com.example.practice_tvshowapp.views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.practice_tvshowapp.adapter.EpisodeViewPagerAdapter
import com.example.practice_tvshowapp.databinding.ActivityEpisodesBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel
import com.example.practice_tvshowapp.factory.EpisodesViewModelFactory
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.types.EpisodesTabsType
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoDate
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoGenres
import com.example.practice_tvshowapp.utils.CommonUtils.getTvShowInfoTime
import com.example.practice_tvshowapp.utils.LoadingUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
    private lateinit var tvShowItem: TvShowItem
    private val tabText = listOf("에피소드", "등장인물")

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
        setTabLayoutViewPager()
        setScrollOnTop()
        subscribeToIsLoadingState()
    }

    private fun setTvShowInfo() {
        binding.infoLayout.apply {
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

    private fun setTabLayoutViewPager() {
        binding.apply {
            episodeViewPager.adapter = EpisodeViewPagerAdapter(this@EpisodesActivity, EpisodesTabsType.values())
            TabLayoutMediator(episodeTabLayout, episodeViewPager) { tab, position ->
                tab.text = tabText[position]
            }.attach()
        }
    }

    private fun setScrollOnTop() {
        binding.episodeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) { }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                binding.episodeNestedScrollView.smoothScrollTo(0, 0)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
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