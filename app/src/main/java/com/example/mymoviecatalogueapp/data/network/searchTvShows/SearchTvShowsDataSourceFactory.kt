package com.example.mymoviecatalogueapp.data.network.searchTvShows

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService

class SearchTvShowsDataSourceFactory(
    private val apiService: TheMovieDBApiService,
    private val query: String
) : DataSource.Factory<Int, TvShow>() {

    val dataSource = MutableLiveData<SearchTvShowDataSource>()

    override fun create(): DataSource<Int, TvShow> {
        val source = SearchTvShowDataSource(apiService, query)
        dataSource.postValue(source)
        return source
    }
}