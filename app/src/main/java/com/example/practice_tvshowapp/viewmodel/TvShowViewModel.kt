package com.example.practice_tvshowapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.practice_tvshowapp.models.tvshows.TvShowContainer
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.repository.TvShowRepository
import com.example.practice_tvshowapp.utils.CommonUtils.getYesterdayDate
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel
@Inject constructor(
    private val repository: TvShowRepository,
):ViewModel() {
    private val _tvShowResponse = MutableLiveData<ArrayList<TvShowContainer>>()
    val tvShowResponse: LiveData<ArrayList<TvShowContainer>>
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
            _tvShowResponse.value = response
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