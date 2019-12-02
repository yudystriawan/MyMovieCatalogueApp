package com.example.mymoviecatalogueapp.data.network.searchMovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService

class SearchMovieDataSourceFactory(
    private val apiService: TheMovieDBApiService,
    private val query: String
) : DataSource.Factory<Int, Movie>() {

    val dataSource = MutableLiveData<SearchMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = SearchMovieDataSource(apiService, query)
        dataSource.postValue(source)
        return source
    }

}