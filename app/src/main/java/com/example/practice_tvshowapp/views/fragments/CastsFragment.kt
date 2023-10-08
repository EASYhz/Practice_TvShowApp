package com.example.practice_tvshowapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practice_tvshowapp.adapter.CastAdapter
import com.example.practice_tvshowapp.databinding.CastContainerLayoutBinding
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel

class CastsFragment : Fragment() {
    lateinit var binding: CastContainerLayoutBinding
    private lateinit var castAdapter: CastAdapter
    private val viewModel: EpisodesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CastContainerLayoutBinding.inflate(inflater, container, false)
        castAdapter = CastAdapter()
        observeCast()

        return binding.root
    }


    override fun onResume() {
        setCastsView()
        super.onResume()
    }

    private fun observeCast() {
        viewModel.tvShowCastResponse.observe(this) { casts ->
            castAdapter.casts = casts
        }
    }

    private fun setCastsView() {
        binding.castsRecyclerView.apply {
            adapter = castAdapter
            layoutManager = GridLayoutManager(
                activity, SPAN_COUNT
            )
            scrollToPosition(0)
        }
    }

    companion object {
        const val SPAN_COUNT = 3
    }

}