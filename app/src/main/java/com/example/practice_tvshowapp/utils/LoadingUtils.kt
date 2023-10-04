package com.example.practice_tvshowapp.utils

import android.view.View
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

class LoadingUtils {

    fun setLoadingView(
        loadingView: ShimmerFrameLayout,
        mainView: View,
        isLoading: Boolean
    ) {
        if(isLoading) {
            loadingView.startShimmer()
            loadingView.visibility = View.VISIBLE

            mainView.visibility = View.INVISIBLE
        } else {
            loadingView.startShimmer()
            loadingView.visibility = View.GONE

            mainView.visibility = View.VISIBLE
        }

    }
}