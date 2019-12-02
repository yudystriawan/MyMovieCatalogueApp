package com.example.mymoviecatalogueapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviecatalogueapp.data.database.MovieDao
import com.example.mymoviecatalogueapp.data.database.TvShowDao
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.POST_PER_PAGE
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService
import com.example.mymoviecatalogueapp.data.network.TheMovieDBDataSource
import com.example.mymoviecatalogueapp.data.network.listMovie.MovieDataSource
import com.example.mymoviecatalogueapp.data.network.listMovie.MovieDataSourceFactory
import com.example.mymoviecatalogueapp.data.network.listMovie.TvShowDataSource
import com.example.mymoviecatalogueapp.data.network.listMovie.TvShowDataSourceFactory
import com.example.mymoviecatalogueapp.data.network.response.MovieNetworkResponse
import com.example.mymoviecatalogueapp.data.network.searchMovie.SearchMovieDataSource
import com.example.mymoviecatalogueapp.data.network.searchMovie.SearchMovieDataSourceFactory
import com.example.mymoviecatalogueapp.data.network.searchTvShows.SearchTvShowDataSource
import com.example.mymoviecatalogueapp.data.network.searchTvShows.SearchTvShowsDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyRepositoryImpl(
    private val movieDataSourceFactory: MovieDataSourceFactory,
    private val tvShowDataSourceFactory: TvShowDataSourceFactory,
    private val theMovieDBDataSource: TheMovieDBDataSource,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) : MyRepository {

    companion object {
        private const val TYPE_MOVIE = "type_movie"
        private const val TYPE_TV_SHOW = "type_tv_show"
    }

    init {
        theMovieDBDataSource.apply {
            downloadedMovieDetailsResponse.observeForever { movieDetails ->
                persistFetchedMovieDetails(movieDetails)
            }
            downloadedTvShowDetailsResponse.observeForever { tvShowDetails ->
                persistFetchedTvShowDetails(tvShowDetails)
            }
        }
    }


    override suspend fun getMovieDetails(movieId: Int): LiveData<Movie> {
        return withContext(Dispatchers.IO) {
            initDetailsData(movieId, TYPE_MOVIE)
            return@withContext movieDao.selectById(movieId)
        }
    }

    override suspend fun getTvShowDetails(tvShowId: Int): LiveData<TvShow> {
        return withContext(Dispatchers.IO) {
            initDetailsData(tvShowId, TYPE_TV_SHOW)
            return@withContext tvShowDao.selectById(tvShowId)
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.update(movie)
        }
    }

    override suspend fun updateTvShow(tvShow: TvShow) {
        GlobalScope.launch(Dispatchers.IO) {
            tvShowDao.update(tvShow)
        }
    }

    override suspend fun getMovieFavorite(): LiveData<List<Movie>> {
        return withContext(Dispatchers.IO) {
            return@withContext movieDao.selectByFavorite()
        }
    }

    override suspend fun getTvShowFavorite(): LiveData<List<TvShow>> {
        return withContext(Dispatchers.IO) {
            return@withContext tvShowDao.selectByFavorite()
        }
    }

    override suspend fun getDailyMovie(
        dateGte: String,
        dateLte: String
    ): LiveData<MovieNetworkResponse> {
        return withContext(Dispatchers.IO) {
            theMovieDBDataSource.fetchDailyMovie(dateGte, dateLte)
            return@withContext theMovieDBDataSource.downloadedDailyMovie
        }
    }

    private fun persistFetchedMovieDetails(movie: Movie?) {
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.insert(movie!!)
        }
    }

    private fun persistFetchedTvShowDetails(tvShowDetails: TvShow?) {
        GlobalScope.launch(Dispatchers.IO) {
            tvShowDao.insert(tvShowDetails!!)
        }
    }

    private suspend fun initDetailsData(id: Int, type: String) {
        when (type) {
            TYPE_MOVIE -> {
                if (isFetchMovieDetailsNeeded(id)) {
                    fetchMovieDetails(id)
                }
            }

            TYPE_TV_SHOW -> {
                if (isFetchTvShowDetailsNeeded(id)) {
                    fetchTvShowDetails(id)
                }
            }

        }

    }

    private suspend fun fetchTvShowDetails(id: Int) {
        theMovieDBDataSource.fetchTvShowDetails(id)
    }

    private fun isFetchTvShowDetailsNeeded(id: Int): Boolean {
        val result = tvShowDao.countById(id)
        return result <= 0
    }

    private suspend fun fetchMovieDetails(movieId: Int) {
        theMovieDBDataSource.fetchMovieDetails(movieId)
    }

    private fun isFetchMovieDetailsNeeded(movieId: Int): Boolean {
        val result = movieDao.countById(movieId)
        return result <= 0

    }

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    override fun fetchLiveMoviePagedList(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    override fun getMoviePagedNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }

    lateinit var tvShowsPagedList: LiveData<PagedList<TvShow>>
    override fun fetchLiveTvShowPagedList(): LiveData<PagedList<TvShow>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        tvShowsPagedList = LivePagedListBuilder(tvShowDataSourceFactory, config).build()

        return tvShowsPagedList
    }

    override fun getTvShowPagedNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<TvShowDataSource, NetworkState>(
            tvShowDataSourceFactory.tvshowsLiveDataSource, TvShowDataSource::networkState
        )
    }

    lateinit var searchMoviePagedList: LiveData<PagedList<Movie>>
    override fun fetchSearchMoviePagedList(query: String): LiveData<PagedList<Movie>> {

        val sourceFactory = SearchMovieDataSourceFactory(TheMovieDBApiService(), query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        searchMoviePagedList = LivePagedListBuilder(sourceFactory, config).build()

        return searchMoviePagedList
    }

    override fun getSearchMovieNetworkState(query: String): LiveData<NetworkState> {
        val sourceFactory = SearchMovieDataSourceFactory(TheMovieDBApiService(), query)
        return Transformations.switchMap<SearchMovieDataSource, NetworkState>(
            sourceFactory.dataSource, SearchMovieDataSource::networkState
        )
    }

    lateinit var searchTvShowPagedList: LiveData<PagedList<TvShow>>
    override fun fetchSearchTvShowPagedList(query: String): LiveData<PagedList<TvShow>> {
        val sourceFactory = SearchTvShowsDataSourceFactory(TheMovieDBApiService(), query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        searchTvShowPagedList = LivePagedListBuilder(sourceFactory, config).build()

        return searchTvShowPagedList
    }

    override fun getSearchTvShowNetworkState(query: String): LiveData<NetworkState> {
        val sourceFactory = SearchTvShowsDataSourceFactory(TheMovieDBApiService(), query)
        return Transformations.switchMap<SearchTvShowDataSource, NetworkState>(
            sourceFactory.dataSource, SearchTvShowDataSource::networkState
        )
    }

}