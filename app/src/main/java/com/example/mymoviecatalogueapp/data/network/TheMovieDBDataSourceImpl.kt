package com.example.mymoviecatalogueapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.response.MovieNetworkResponse

class TheMovieDBDataSourceImpl(
    private val apiService: TheMovieDBApiService
) : TheMovieDBDataSource {

    companion object {
        private val TAG = TheMovieDBDataSourceImpl::class.java.simpleName
    }

    private val _downloadedMovieDetailsResponse = MutableLiveData<Movie>()
    override val downloadedMovieDetailsResponse: LiveData<Movie>
        get() = _downloadedMovieDetailsResponse

    override suspend fun fetchMovieDetails(movieId: Int) {
        try {
            val fetchedMovieDetails = apiService.getMovieDetails(movieId)
            if (fetchedMovieDetails.isSuccessful) {
                _downloadedMovieDetailsResponse.postValue(fetchedMovieDetails.body())
            } else {
                Log.e(TAG, fetchedMovieDetails.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private val _downloadedTvShowDetailsResponse = MutableLiveData<TvShow>()
    override val downloadedTvShowDetailsResponse: LiveData<TvShow>
        get() = _downloadedTvShowDetailsResponse

    override suspend fun fetchTvShowDetails(tvShowId: Int) {
        try {
            val fetchedTvShowDetails = apiService.getTvShowDetails(tvShowId)
            if (fetchedTvShowDetails.isSuccessful) {
                _downloadedTvShowDetailsResponse.postValue(fetchedTvShowDetails.body())
            } else {
                Log.e(TAG, fetchedTvShowDetails.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val _downloadedDailyMovie = MutableLiveData<MovieNetworkResponse>()
    override val downloadedDailyMovie: LiveData<MovieNetworkResponse>
        get() = _downloadedDailyMovie

    override suspend fun fetchDailyMovie(dateGte: String, dateLte: String) {
        try {
            val fetchedDailyMovie = apiService.getDailyMovie(dateGte, dateLte)

            if (fetchedDailyMovie.isSuccessful){
                _downloadedDailyMovie.postValue(fetchedDailyMovie.body())
            }else {
                Log.e(TAG, fetchedDailyMovie.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}