package com.example.mymoviecatalogueapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.response.MovieNetworkResponse

interface MyRepository {

    fun fetchLiveMoviePagedList(): LiveData<PagedList<Movie>>
    fun getMoviePagedNetworkState(): LiveData<NetworkState>

    fun fetchLiveTvShowPagedList(): LiveData<PagedList<TvShow>>
    fun getTvShowPagedNetworkState(): LiveData<NetworkState>

    suspend fun getMovieDetails(movieId: Int): LiveData<Movie>
    suspend fun getTvShowDetails(tvShowId: Int): LiveData<TvShow>

    suspend fun updateMovie(movie: Movie)
    suspend fun updateTvShow(tvShow: TvShow)

    suspend fun getMovieFavorite(): LiveData<List<Movie>>
    suspend fun getTvShowFavorite(): LiveData<List<TvShow>>

    fun fetchSearchMoviePagedList(query: String): LiveData<PagedList<Movie>>
    fun getSearchMovieNetworkState(query: String): LiveData<NetworkState>

    fun fetchSearchTvShowPagedList(query: String): LiveData<PagedList<TvShow>>
    fun getSearchTvShowNetworkState(query: String): LiveData<NetworkState>

    suspend fun getDailyMovie(dateGte: String, dateLte: String): LiveData<MovieNetworkResponse>


}