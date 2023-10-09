package com.example.practice_tvshowapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practice_tvshowapp.types.EpisodesTabsType

class EpisodeViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val tabs : Array<EpisodesTabsType>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return tabs[position].fragment
    }
}

