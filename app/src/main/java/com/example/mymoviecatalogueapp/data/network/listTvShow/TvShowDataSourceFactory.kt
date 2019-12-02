package com.example.mymoviecatalogueapp.data.network.listMovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService

class TvShowDataSourceFactory(
    private val apiService: TheMovieDBApiService
) : DataSource.Factory<Int, TvShow>() {

    val tvshowsLiveDataSource = MutableLiveData<TvShowDataSource>()

    override fun create(): DataSource<Int, TvShow> {
        val tvShowDataSource =
            TvShowDataSource(
                apiService
            )

        tvshowsLiveDataSource.postValue(tvShowDataSource)

        return tvShowDataSource

    }

}