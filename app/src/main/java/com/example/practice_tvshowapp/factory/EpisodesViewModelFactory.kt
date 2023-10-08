package com.example.practice_tvshowapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practice_tvshowapp.viewmodel.EpisodesViewModel
import dagger.assisted.AssistedFactory

class EpisodesViewModelFactory {

    @AssistedFactory
    interface ViewModelFactory {
        fun create(tvShowId: Int): EpisodesViewModel

    }

    companion object {
        fun provideFactory(viewModelFactory: ViewModelFactory, tvShowId: Int) : ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModelFactory.create(tvShowId) as T
                }
            }
        
        
    }
}
