package com.example.mymoviecatalogueapp.data.network.searchMovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.network.FIRST_PAGE
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService
import com.example.mymoviecatalogueapp.repository.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMovieDataSource(
    private val apiService: TheMovieDBApiService,
    private val query: String
) : PageKeyedDataSource<Int, Movie>() {

    private val page = FIRST_PAGE
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovies = apiService.getSearchMovies(page, query)
                if (fetchedMovies.isSuccessful) {
                    val response = fetchedMovies.body()!!
                    val list = response.results

                    callback.onResult(list, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }

            } catch (e: Exception) {
                networkState.postValue(NetworkState.ERROR)
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovies = apiService.getSearchMovies(params.key, query)
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
                networkState.postValue(NetworkState.ERROR)
                e.printStackTrace()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // do nothing
    }
}