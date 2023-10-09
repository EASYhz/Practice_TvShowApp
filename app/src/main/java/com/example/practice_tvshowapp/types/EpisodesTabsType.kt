package com.example.practice_tvshowapp.types

import androidx.fragment.app.Fragment
import com.example.practice_tvshowapp.views.fragments.CastsFragment
import com.example.practice_tvshowapp.views.fragments.EpisodesFragment

enum class EpisodesTabsType(val tabTitle: String , val fragment: Fragment) {
    EPISODE(tabTitle = "에피소드", fragment = EpisodesFragment()),
    CAST(tabTitle = "등장인물", fragment = CastsFragment())
}