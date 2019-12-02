package com.example.mymoviecatalogueapp.data.network.listMovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.FIRST_PAGE
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService
import com.example.mymoviecatalogueapp.repository.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowDataSource(
    private val apiService: TheMovieDBApiService
) : PageKeyedDataSource<Int, TvShow>() {

    private val page = FIRST_PAGE
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {
        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovies = apiService.getTvShows(page)
                if (fetchedMovies.isSuccessful) {
                    val response = fetchedMovies.body()!!
                    val list = response.results

                    callback.onResult(list, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovies = apiService.getTvShows(params.key)
                if (fetchedMovies.isSuccessful) {
                    val response = fetchedMovies.body()!!
                    val list = response.results

                    if (response.totalPages >= params.key) {
                        callback.onResult(list, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {

    }
}