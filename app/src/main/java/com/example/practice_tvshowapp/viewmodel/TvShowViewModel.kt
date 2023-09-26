package com.example.practice_tvshowapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.repository.TvShowRepository
import com.example.practice_tvshowapp.utils.CommonUtils.getYesterdayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel
@Inject constructor(
    private val repository: TvShowRepository
):ViewModel() {

    private val _tvShowResponse = MutableLiveData<List<TvShowItem>>()
    val tvShowResponse: LiveData<List<TvShowItem>>
        get() = _tvShowResponse

    private val _webTvShowResponse = MutableLiveData<List<TvShowItem>>()
    val webTvShowResponse : LiveData<List<TvShowItem>>
        get() = _webTvShowResponse

    init {
        getAllTvShows()
        getAllWebTvShows()
    }

    private fun getAllTvShows() = viewModelScope.launch {
        repository.getAllTvShows().let { response ->
            if(response.isSuccessful) {
                _tvShowResponse.postValue(response.body())
            } else {
                Log.d("TvShowViewModel >> ", "getAllTvShows Error: ${response.code()}")
            }
        }
    }

    private fun getAllWebTvShows() = viewModelScope.launch {
        repository.getWebTvShowOnYesterday(getYesterdayDate()).let { response ->
            if(response.isSuccessful) {
                _webTvShowResponse.postValue(response.body())
            } else {
                Log.d("TvShowViewModel >> ", "getAllWebTvShows Error: ${response.code()}")
            }
        }
    }

}