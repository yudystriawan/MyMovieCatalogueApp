package com.example.mymoviecatalogueapp.data.network

import androidx.lifecycle.LiveData
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.response.MovieNetworkResponse

interface TheMovieDBDataSource {

    val downloadedMovieDetailsResponse: LiveData<Movie>
    suspend fun fetchMovieDetails(movieId: Int)

    val downloadedTvShowDetailsResponse: LiveData<TvShow>
    suspend fun fetchTvShowDetails(tvShowId: Int)

    val downloadedDailyMovie: LiveData<MovieNetworkResponse>
    suspend fun fetchDailyMovie(dateGte: String, dateLte: String)

}