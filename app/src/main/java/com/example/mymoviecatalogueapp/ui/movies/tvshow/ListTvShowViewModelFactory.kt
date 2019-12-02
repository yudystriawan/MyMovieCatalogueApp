package com.example.mymoviecatalogueapp.ui.movies.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviecatalogueapp.repository.MyRepository

class ListTvShowViewModelFactory(
    private val repository: MyRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListTvShowViewModel(
            repository
        ) as T
    }
}