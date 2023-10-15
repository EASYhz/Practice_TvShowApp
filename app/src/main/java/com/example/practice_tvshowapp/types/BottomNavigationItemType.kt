package com.example.practice_tvshowapp.types

import androidx.fragment.app.Fragment
import com.example.practice_tvshowapp.R
import com.example.practice_tvshowapp.views.fragments.SearchFragment
import com.example.practice_tvshowapp.views.fragments.TvShowsFragment

enum class BottomNavigationItemType(val itemId : Int, val fragment: Fragment) {
    HOME(itemId = R.id.bottomNavHome, fragment = TvShowsFragment()),
    SEARCH(itemId = R.id.bottomNavSearch, fragment = SearchFragment())
}