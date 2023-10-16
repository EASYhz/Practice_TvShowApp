package com.example.practice_tvshowapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.practice_tvshowapp.models.tvshows.SearchTvShow
import com.example.practice_tvshowapp.models.tvshows.SearchTvShowItem
import com.example.practice_tvshowapp.models.tvshows.TvShowContainer
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import com.example.practice_tvshowapp.repository.TvShowRepository
import com.example.practice_tvshowapp.utils.CommonUtils.getYesterdayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _searchTvShowResponse = MutableLiveData<ArrayList<SearchTvShowItem>>()
    val searchTvShowResponse: LiveData<ArrayList<SearchTvShowItem>>
        get() = _searchTvShowResponse

    private val _isLoadingState = MutableStateFlow(true)
    val isLoadingState : StateFlow<Boolean>
        get() = _isLoadingState

    private val _isEmptyState = MutableStateFlow(false)
    val isEmptyState : StateFlow<Boolean>
        get() = _isEmptyState

    init {
        getAllTvShows()
        getAllWebTvShows()
    }

    private fun getAllTvShows() = viewModelScope.launch {
        repository.getAllTvShows().let { response ->
            _tvShowResponse.value = response
            _isLoadingState.value = false
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

    fun searchTvShows(terms: String) = viewModelScope.launch {
        _isLoadingState.value = true
        repository.searchTvShows(terms = terms).let { response ->
            if(response.isSuccessful) {
                Log.d("aaaaa", "sdfjsdaofijwoweifk 1")
                _isLoadingState.value = false
                setEmptyText()
                _searchTvShowResponse.postValue(response.body())
            } else {
                Log.d("TvShowViewModel >> ", "searchTvShows Error: ${response.code()}")
            }
        }
    }

    fun setIsLoading() {
        _isLoadingState.value = true
        _searchTvShowResponse.postValue(SearchTvShow())
        setEmptyText()
    }

    private fun setEmptyText() {
        _isEmptyState.value = (!_isLoadingState.value && _searchTvShowResponse.value.isNullOrEmpty())
    }

}