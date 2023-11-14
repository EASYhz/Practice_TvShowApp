package com.example.practice_tvshowapp.views.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practice_tvshowapp.adapter.CastAdapter
import com.example.practice_tvshowapp.databinding.CastContainerLayoutBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel
import com.example.practice_tvshowapp.views.EpisodesActivity
import dagger.hilt.android.scopes.FragmentScoped
import kotlin.properties.Delegates

@FragmentScoped
class CastsFragment : Fragment() {
    lateinit var binding: CastContainerLayoutBinding
    private lateinit var castAdapter: CastAdapter
    private lateinit var viewModel: EpisodesViewModel
    private var currentOrientation by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CastContainerLayoutBinding.inflate(inflater, container, false)
        castAdapter = CastAdapter()
        viewModel = ViewModelProvider(activity as EpisodesActivity)[EpisodesViewModel::class.java]
        currentOrientation = resources.configuration.orientation

        observeCast()
        setCastsView()

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
    private fun observeCast() {
        viewModel.tvShowCastResponse.observe(viewLifecycleOwner) { casts ->
            castAdapter.casts = casts
        }
    }

    private fun setCastsView() {
        binding.castsRecyclerView.apply {
            adapter = castAdapter
            layoutManager = GridLayoutManager(
                activity, setSpanCount()
            )
            scrollToPosition(0)
        }
    }

    private fun setSpanCount() : Int = when(currentOrientation) {
        Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_SPAN_COUNT
        Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_SPAN_COUNT
        else -> PORTRAIT_SPAN_COUNT
    }

    companion object {
        const val PORTRAIT_SPAN_COUNT = 3
        const val LANDSCAPE_SPAN_COUNT = 5
    }
}