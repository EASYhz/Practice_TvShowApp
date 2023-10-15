package com.example.practice_tvshowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.practice_tvshowapp.databinding.ActivityMainBinding
import com.example.practice_tvshowapp.views.fragments.SearchFragment
import com.example.practice_tvshowapp.views.fragments.TvShowsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(TvShowsFragment())
        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.bottomNavHome -> replaceFragment(TvShowsFragment())
                R.id.bottomNavSearch -> replaceFragment(SearchFragment())
                else -> {}
            }

            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.commit()
    }
}