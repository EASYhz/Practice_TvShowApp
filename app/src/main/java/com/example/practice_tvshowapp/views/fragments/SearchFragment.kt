package com.example.practice_tvshowapp.views.fragments

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practice_tvshowapp.MainActivity
import com.example.practice_tvshowapp.adapter.TvShowAdapter
import com.example.practice_tvshowapp.databinding.FragmentSearchBinding
import com.example.practice_tvshowapp.databinding.SearchActionbarLayoutBinding
import com.example.practice_tvshowapp.models.tvshows.SearchTvShow
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.utils.LoadingUtils.subscribeToStateFlowVisibility
import com.example.practice_tvshowapp.viewmodel.TvShowViewModel
import com.example.practice_tvshowapp.views.EpisodesActivity
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.*

@FragmentScoped
class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var searchBinding : SearchActionbarLayoutBinding
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding = SearchActionbarLayoutBinding.inflate(layoutInflater, null, false)
        searchView = searchBinding.searchView

        tvShowViewModel = ViewModelProvider(activity as MainActivity)[TvShowViewModel::class.java]

        setActionBar()
        setUp()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        searchView.clearFocus()
    }
    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            customView = searchBinding.root
            customView.layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
        }
    }

    private fun setUp() {
        setSearchView()
        setBackButton()
        setSearchTvShowView()
        observeSearchTvShowResponse()
        subscribeToStateFlowVisibility(
            stateFlow = tvShowViewModel.isLoadingState,
            view = binding.circleLoading,
            lifecycleScope = lifecycleScope
        ) { it -> it }
        subscribeToStateFlowVisibility(
            stateFlow = tvShowViewModel.isEmptyState,
            view = binding.emptyTextView,
            lifecycleScope = lifecycleScope
        ) { it -> it }
    }

    private fun setSearchView() {
        searchView.apply {
            isIconified = false
            requestFocus()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                private var job: Job? = null

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchTvShows(p0)
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    job?.cancel() // 이전 작업 취소
                    tvShowViewModel.setIsLoading()
                    job = CoroutineScope(Dispatchers.Main).launch {
                        delay(1500) // 1.5초 지연
                        searchTvShows(p0)
                    }
                    return true
                }
            })
        }
    }

    private fun searchTvShows(terms: String?) {
        terms?.let {
            tvShowViewModel.searchTvShows(it)
        }
    }

    private fun setBackButton() {
        searchBinding.backButton.apply {
            setOnClickListener {
                searchView.clearFocus()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchTvShowView() {
        tvShowAdapter = TvShowAdapter { item ->
            onClickTvShowItem(item)
        }

        binding.searchRecyclerView.apply {
            adapter = tvShowAdapter
            layoutManager = GridLayoutManager(activity, 3)

            setOnTouchListener { _, _ ->
                searchView.clearFocus()
                false
            }
        }
    }

    private fun onClickTvShowItem(tvShowItem: TvShowItem) {
        val tvShowIntent = Intent(activity, EpisodesActivity::class.java)
        tvShowIntent.putExtra("tvShowItem", tvShowItem)
        startActivity(tvShowIntent)
    }

    private fun observeSearchTvShowResponse() {
        tvShowViewModel.searchTvShowResponse.observe(viewLifecycleOwner) {
            tvShowAdapter.tvShows = filterTvShows(it as SearchTvShow)
        }
    }

//    private fun subscribeToIsLoadingState() = lifecycleScope.launch {
//        tvShowViewModel.isLoadingState.collectLatest {
//            binding.circleLoading.visibility = if(it) View.VISIBLE else View.INVISIBLE
//        }
//    }
//
//    private fun subscribeIsEmptyState() = lifecycleScope.launch {
//        tvShowViewModel.isEmptyState.collectLatest {
//            binding.emptyTextView.visibility = if(it) View.VISIBLE else View.INVISIBLE
//        }
//    }

    private fun filterTvShows(searchTvShow: SearchTvShow): List<TvShowItem> = searchTvShow.map { it.show }
}