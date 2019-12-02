package com.example.mymoviecatalogueapp.data.network.listMovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService

class MovieDataSourceFactory(
    private val apiService: TheMovieDBApiService
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource =
            MovieDataSource(apiService)

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource

    }

}