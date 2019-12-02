package com.example.mymoviecatalogueapp.data.network

import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.response.MovieNetworkResponse
import com.example.mymoviecatalogueapp.data.network.response.TvShowNetworkResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//https://api.themoviedb.org/3/discover/movie?api_key=d8f112a69e3918d618e1be1a274830eb&language=en-US
//https://api.themoviedb.org/3/discover/movie?api_key={API KEY}&primary_release_date.gte={TODAY DATE}&primary_release_date.lte={TODAY DATE}
private const val API_KEY = "d8f112a69e3918d618e1be1a274830eb"
private const val BASE_URL = "https://api.themoviedb.org/3/"

const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w342"
const val BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20

interface TheMovieDBApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MovieNetworkResponse>

    @GET("discover/tv")
    suspend fun getTvShows(
        @Query("page") page: Int
    ): Response<TvShowNetworkResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Response<Movie>

    @GET("tv/{tvShow_id}")
    suspend fun getTvShowDetails(
        @Path("tvShow_id") id: Int
    ): Response<TvShow>

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<MovieNetworkResponse>

    @GET("search/tv")
    suspend fun getSearchTvShows(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<TvShowNetworkResponse>

    @GET("discover/movie")
    suspend fun getDailyMovie(
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Response<MovieNetworkResponse>

    companion object {
        operator fun invoke(): TheMovieDBApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDBApiService::class.java)
        }
    }

}