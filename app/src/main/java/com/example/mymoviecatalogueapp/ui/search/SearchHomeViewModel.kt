package com.example.mymoviecatalogueapp.ui.search

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalogueapp.repository.MyRepository

class SearchHomeViewModel(
    private val repository: MyRepository,
    private val query: String
) : ViewModel() {

    val moviePagedList by lazy {
//        repository.fetchLiveMoviePagedList()
        repository.fetchSearchMoviePagedList(query)
    }

    val networkState by lazy {
//        repository.getMoviePagedNetworkState()
        repository.getSearchMovieNetworkState(query)
    }

    fun isListEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    val tvShowPagedList by lazy {
//        repository.fetchLiveMoviePagedList()
        repository.fetchSearchTvShowPagedList(query)
    }

    val tvShowNetworkState by lazy {
//        repository.getMoviePagedNetworkState()
        repository.getSearchTvShowNetworkState(query)
    }

    fun isListTvShowEmpty(): Boolean {
        return tvShowPagedList.value?.isEmpty() ?: true
    }

}
