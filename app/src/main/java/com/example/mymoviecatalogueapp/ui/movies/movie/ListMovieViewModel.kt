package com.example.mymoviecatalogueapp.ui.movie

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalogueapp.repository.MyRepository

class ListMovieViewModel(
    private val repository: MyRepository
) : ViewModel() {

    val moviePagedList by lazy {
        repository.fetchLiveMoviePagedList()
    }

    val networkState by lazy {
        repository.getMoviePagedNetworkState()
    }

    fun isListEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

}
