package com.example.practice_tvshowapp.types

import androidx.fragment.app.Fragment
import com.example.practice_tvshowapp.views.fragments.CastsFragment
import com.example.practice_tvshowapp.views.fragments.EpisodesFragment

enum class EpisodesTabsType(val fragment: Fragment) {
    EPISODE(EpisodesFragment()),
    CAST(CastsFragment())
}