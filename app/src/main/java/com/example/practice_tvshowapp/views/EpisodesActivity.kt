package com.example.practice_tvshowapp.views

import android.content.Intent
import android.net.Uri
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
import com.example.practice_tvshowapp.utils.CommonUtils.convertDpToPx
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
    private lateinit var episodeViewModel: EpisodesViewModel
    @Inject lateinit var episodesViewModelFactory: EpisodesViewModelFactory.ViewModelFactory
    private lateinit var tvShowItem: TvShowItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val episodesIntent = intent
        tvShowItem = episodesIntent.serializable("tvShowItem")!!

        episodeViewModel = viewModels<EpisodesViewModel> {
            EpisodesViewModelFactory.provideFactory(episodesViewModelFactory, tvShowItem.id)
        }.value
        episodeViewModel.setTvShowInfo(tvShowItem)

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
            viewModel = episodeViewModel
            moreTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tvShowItem.url))
                startActivity(intent)
            }
        }
    }

    private fun setTabLayoutViewPager() {
        binding.apply {
            episodeViewPager.adapter = EpisodeViewPagerAdapter(this@EpisodesActivity, EpisodesTabsType.values())
            TabLayoutMediator(episodeTabLayout, episodeViewPager) { tab, position ->
                tab.text = EpisodesTabsType.values()[position].tabTitle
            }.attach()
        }
    }

    private fun setScrollOnTop() {
        binding.apply {
            episodeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) { }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    if(episodeNestedScrollView.isHeaderSticky) {
                        episodeNestedScrollView.smoothScrollTo(0, infoLayout.root.height + convertDpToPx(10, this@EpisodesActivity))
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) { }

            })
        }
    }
    private fun subscribeToIsLoadingState() {
        lifecycleScope.launch {
            episodeViewModel.isLoadingState.collectLatest { isLoading ->
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