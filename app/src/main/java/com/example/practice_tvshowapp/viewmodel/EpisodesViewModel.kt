package com.example.practice_tvshowapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.practice_tvshowapp.models.casts.CastItem
import com.example.practice_tvshowapp.models.episodes.EpisodeItem
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.repository.TvShowRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodesViewModel
@AssistedInject
constructor(
    private val repository: TvShowRepository,
    @Assisted private val tvShowId: Int) : ViewModel() {

    private val _tvShowEpisodeResponse = MutableLiveData<List<EpisodeItem>>()
    val tvShowEpisodeResponse : LiveData<List<EpisodeItem>>
        get() = _tvShowEpisodeResponse

    private val _tvShowCastResponse = MutableLiveData<List<CastItem>>()
    val tvShowCastResponse : LiveData<List<CastItem>>
        get() = _tvShowCastResponse

    private val _isLoadingState = MutableStateFlow(true)
    val isLoadingState : StateFlow<Boolean>
        get() = _isLoadingState

    private val _tvShowInfo = MutableLiveData<TvShowItem>()
    val tvShowInfo : LiveData<TvShowItem>
        get() = _tvShowInfo

    init {
        getEpisodes(tvShowId)
        getCasts(tvShowId)
    }

    private fun getEpisodes(tvShowId: Int) = viewModelScope.launch {
        repository.getEpisodes(tvShowId).let { response ->
            if(response.isSuccessful) {
                _tvShowEpisodeResponse.postValue(response.body())
                _isLoadingState.value = false
            } else Log.d("EpisodesViewModel > ", "getTvShowEpisodes Error : ${response.code()}")
        }
    }

    private fun getCasts(tvShowId: Int) = viewModelScope.launch {
        repository.getCasts(tvShowId).let { response ->
            if(response.isSuccessful) {
                _tvShowCastResponse.postValue(response.body())
            } else Log.d("EpisodesViewModel > ", "getCasts Error : ${response.code()}")
        }
    }

    fun setTvShowInfo(data: TvShowItem) {
        _tvShowInfo.value = data
    }

}