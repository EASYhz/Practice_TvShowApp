package com.example.practice_tvshowapp.utils

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object LoadingUtils {
    fun <T> subscribeToStateFlowShimmer(
        stateFlow: StateFlow<T>,
        loadingView: ShimmerFrameLayout,
        mainView: View,
        lifecycleScope: LifecycleCoroutineScope,
        visibilityCondition: (T) -> Boolean
    ) = lifecycleScope.launch {
        stateFlow.collectLatest { isLoading ->
            if(visibilityCondition(isLoading)) {
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

    fun <T> subscribeToStateFlowVisibility(
        stateFlow: StateFlow<T>,
        view: View,
        lifecycleScope: LifecycleCoroutineScope,
        visibilityCondition: (T) -> Boolean
    ) = lifecycleScope.launch {
        stateFlow.collectLatest {
            view.visibility = if (visibilityCondition(it)) View.VISIBLE else View.INVISIBLE
        }
    }
}