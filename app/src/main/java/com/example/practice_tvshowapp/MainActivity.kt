package com.example.practice_tvshowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.adapter.TvShowAdapter
import com.example.practice_tvshowapp.databinding.ActivityMainBinding
import com.example.practice_tvshowapp.viewmodel.TvShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tvShowViewModel: TvShowViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tvShowAdapter = TvShowAdapter()

        binding.tvShowRecyclerView.apply {
            adapter = tvShowAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )

            setHasFixedSize(true)
        }


        tvShowViewModel.tvShowResponse.observe(this) { tvShowItem ->
            tvShowAdapter.tvShows = tvShowItem
        }
    }
}