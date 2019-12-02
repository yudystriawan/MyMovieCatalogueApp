package com.example.mymoviecatalogueapp.ui.detail

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.internal.lazyDeferred
import com.example.mymoviecatalogueapp.repository.MyRepository

class DetailsViewModel(
    private val repository: MyRepository,
    private val id: Int
) : ViewModel() {

    val movie by lazyDeferred {
        repository.getMovieDetails(id)
    }

    val tvShow by lazyDeferred {
        repository.getTvShowDetails(id)
    }

    suspend fun updateMovie(movie: Movie){
        repository.updateMovie(movie)
    }

    suspend fun updateTvShow(tvShow: TvShow){
        repository.updateTvShow(tvShow)
    }

}