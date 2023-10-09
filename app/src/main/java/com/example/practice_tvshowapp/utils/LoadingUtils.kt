package com.example.practice_tvshowapp.utils

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

object LoadingUtils {

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
            loadingView.stopShimmer()
            loadingView.visibility = View.GONE

            mainView.visibility = View.VISIBLE
        }

    }
}