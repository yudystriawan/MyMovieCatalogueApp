package com.example.mymoviecatalogueapp.ui.movies.tvshow

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalogueapp.repository.MyRepository

class ListTvShowViewModel(
    private val repository: MyRepository
) : ViewModel() {

    val tvShowPagedList by lazy {
        repository.fetchLiveTvShowPagedList()
    }

    val networkState by lazy {
        repository.getTvShowPagedNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return tvShowPagedList.value?.isEmpty() ?: true
    }

}
