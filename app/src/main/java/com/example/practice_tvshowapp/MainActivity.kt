package com.example.practice_tvshowapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.adapter.TvShowAdapter
import com.example.practice_tvshowapp.adapter.TvShowContainerAdapter
import com.example.practice_tvshowapp.databinding.ActivityMainBinding
import com.example.practice_tvshowapp.models.tvshows.TvShowContainer
import com.example.practice_tvshowapp.utils.LoadingUtils
import com.example.practice_tvshowapp.viewmodel.TvShowViewModel
import com.example.practice_tvshowapp.views.EpisodesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tvShowViewModel: TvShowViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowContainerAdapter: TvShowContainerAdapter
    private val loadingUtils: LoadingUtils = LoadingUtils()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       setUp()
    }

    private fun setUp() {
        setTvShowView()
        observeTvShowItem()
        subscribeToIsLoadingState()
    }

    private fun setTvShowView() {
        tvShowContainerAdapter = TvShowContainerAdapter { item ->
            onClickTvShowItem(item.id)
        }

        binding.tvShowContainerRecyclerView.apply {
            adapter = tvShowContainerAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
    }


    private fun observeTvShowItem() {
        tvShowViewModel.tvShowResponse.observe(this) { tvShowItem ->
            tvShowContainerAdapter.tvShowContainers = tvShowItem
        }
    }

   private fun onClickTvShowItem(tvShowId: Int) {
       val tvShowIntent = Intent(this, EpisodesActivity::class.java)
        tvShowIntent.putExtra("tvShowId", tvShowId)
        startActivity(tvShowIntent)
    }

    private fun subscribeToIsLoadingState() {
        lifecycleScope.launch {
            tvShowViewModel.isLoadingState.collectLatest { isLoading ->
                loadingUtils.setLoadingView(
                    loadingView = binding.tvShowSkeletonLoadingView,
                    mainView = binding.tvShowContainerRecyclerView,
                    isLoading = isLoading
                )
            }
        }
    }
}