package com.example.practice_tvshowapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel
@Inject constructor(
    private val repository: TvShowRepository
):ViewModel() {

    private val _response = MutableLiveData<List<TvShowItem>>()
    val tvShowResponse: LiveData<List<TvShowItem>>
        get() = _response

    init {
        getAllTvShows()
    }

    private fun getAllTvShows() = viewModelScope.launch {
        repository.getAllTvShows().let { response ->
            if(response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d("TvShowViewModel >> ", "getAllTvShows Error: ${response.code()}")
            }

        }
    }

}