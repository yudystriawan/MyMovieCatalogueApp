package com.example.mymoviecatalogueapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviecatalogueapp.repository.MyRepository

class ListMovieViewModelFactory(
    private val repository: MyRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListMovieViewModel(
            repository
        ) as T
    }
}