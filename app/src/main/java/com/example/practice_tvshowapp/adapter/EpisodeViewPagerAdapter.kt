package com.example.practice_tvshowapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practice_tvshowapp.types.EpisodesTabsType
import com.example.practice_tvshowapp.views.fragments.CastsFragment
import com.example.practice_tvshowapp.views.fragments.EpisodesFragment

class EpisodeViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = VIEW_COUNT

    // TODO : 리팩토링
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            EpisodesTabsType.EPISODE.ordinal -> EpisodesFragment()
            EpisodesTabsType.CAST.ordinal -> CastsFragment()
            else -> EpisodesFragment()
        }
    }


    companion object {
        const val VIEW_COUNT = 2
    }
}
