package com.example.practice_tvshowapp.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.MainActivity
import com.example.practice_tvshowapp.adapter.TvShowContainerAdapter
import com.example.practice_tvshowapp.databinding.FragmentTvShowsBinding
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.utils.LoadingUtils
import com.example.practice_tvshowapp.viewmodel.TvShowViewModel
import com.example.practice_tvshowapp.views.EpisodesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment() {
    private lateinit var binding: FragmentTvShowsBinding
    private lateinit var tvShowContainerAdapter: TvShowContainerAdapter
    private lateinit var tvShowViewModel: TvShowViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowsBinding.inflate(inflater, container, false)

        tvShowViewModel = ViewModelProvider(activity as MainActivity)[TvShowViewModel::class.java]

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_TITLE
        }

        setUp()

        return binding.root
    }

    private fun setUp() {
        setTvShowView()
        observeTvShowItem()
        subscribeToIsLoadingState()
    }

    private fun setTvShowView() {
        tvShowContainerAdapter = TvShowContainerAdapter { item ->
            onClickTvShowItem(item)
        }

        binding.tvShowContainerRecyclerView.apply {
            adapter = tvShowContainerAdapter
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL,
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

    private fun onClickTvShowItem(tvShowItem: TvShowItem) {
        tvShowViewModel.insertTvShow(tvShowItem)
        val tvShowIntent = Intent(activity, EpisodesActivity::class.java)
        tvShowIntent.putExtra("tvShowItem", tvShowItem)
        startActivity(tvShowIntent)
    }

    private fun subscribeToIsLoadingState() {
        binding.apply {
            LoadingUtils.subscribeToStateFlowShimmer(
                stateFlow = tvShowViewModel.isLoadingState,
                loadingView = tvShowSkeletonLoadingView,
                mainView = tvShowContainerRecyclerView,
                lifecycleScope = lifecycleScope
            ) { it -> it }
        }
    }

}