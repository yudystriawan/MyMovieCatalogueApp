package com.example.mymoviecatalogueapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalogueapp.internal.lazyDeferred
import com.example.mymoviecatalogueapp.repository.MyRepository

class FavoriteHomeViewModel(
    private val repository: MyRepository
) : ViewModel() {

    val movies by lazyDeferred {
        repository.getMovieFavorite()
    }

    val tvShows by lazyDeferred {
        repository.getTvShowFavorite()
    }

}