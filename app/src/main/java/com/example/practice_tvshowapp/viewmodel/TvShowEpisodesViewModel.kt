package com.example.practice_tvshowapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.practice_tvshowapp.models.episodes.TvShowEpisodeItem
import com.example.practice_tvshowapp.repository.TvShowRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TvShowEpisodesViewModel
@AssistedInject
constructor(
    private val repository: TvShowRepository,
    @Assisted private val tvShowId: Int) : ViewModel() {

    private val _tvShowEpisodeResponse = MutableLiveData<List<TvShowEpisodeItem>>()
    val tvShowEpisodeResponse : LiveData<List<TvShowEpisodeItem>>
        get() = _tvShowEpisodeResponse

    init {
        getTvShowEpisodes(tvShowId)
    }

    private fun getTvShowEpisodes(tvShowId: Int) = viewModelScope.launch {
        repository.getTvShowEpisodes(tvShowId).let { response ->
            if(response.isSuccessful) _tvShowEpisodeResponse.postValue(response.body())
            else Log.d("TvShowEpisodesViewModel > ", "getTvShowEpisodes Error : ${response.code()}")
        }
    }


    @AssistedFactory
    interface TvShowEpisodesViewModelFactory {
        fun create(tvShowId: Int): TvShowEpisodesViewModel
    }

    companion object {
        fun provideFactory(tvShowEpisodesViewModelFactory: TvShowEpisodesViewModelFactory, tvShowId: Int) : ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return tvShowEpisodesViewModelFactory.create(tvShowId) as T
                }
            }
    }
}