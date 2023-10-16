package com.example.practice_tvshowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.practice_tvshowapp.databinding.ActivityMainBinding
import com.example.practice_tvshowapp.types.BottomNavigationItemType
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

        getFragment(R.id.bottomNavHome)?.let { replaceFragment(it) }

        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            getFragment(menuItem.itemId)?.let { replaceFragment(it) }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun getFragment(id: Int) = BottomNavigationItemType.values().find { it.itemId == id }?.fragment
}