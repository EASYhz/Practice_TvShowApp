package com.example.practice_tvshowapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_tvshowapp.adapter.TvShowAdapter
import com.example.practice_tvshowapp.databinding.ActivityMainBinding
import com.example.practice_tvshowapp.viewmodel.TvShowViewModel
import com.example.practice_tvshowapp.views.TvShowDetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tvShowViewModel: TvShowViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var webTvShowAdapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()

    }

    private fun setUp() {
        setTvShowView()
        setTvShowViewModel()
        setWebTvShowView()
        setWebTvShowViewModel()
    }

    private fun setTvShowView() {
        tvShowAdapter = TvShowAdapter(onItemClickListener = { item ->
            onClickTvShowItem(item.id)
        })

        binding.tvShowRecyclerView.apply {
            adapter = tvShowAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    private fun setTvShowViewModel() {
        tvShowViewModel.tvShowResponse.observe(this) { tvShowItem ->
            tvShowAdapter.tvShows = tvShowItem
        }
    }

    private fun setWebTvShowView() {

        webTvShowAdapter = TvShowAdapter(onItemClickListener = { item ->
            Snackbar.make(binding.root, "\'${item.name}\' 에 대한 정보가 없습니다.", 5000).show()
        })

        binding.webTvShowRecyclerView.apply {
            adapter = webTvShowAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun setWebTvShowViewModel() {
        tvShowViewModel.webTvShowResponse.observe(this) { webTvShowItem ->
            webTvShowAdapter.tvShows = webTvShowItem
        }
    }

   private fun onClickTvShowItem(tvShowId: Int) {
       val tvShowIntent = Intent(this, TvShowDetailActivity::class.java)
        tvShowIntent.putExtra("tvShowId", tvShowId)
        startActivity(tvShowIntent)
    }
}