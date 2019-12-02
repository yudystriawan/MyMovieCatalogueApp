package com.example.mymoviecatalogueapp

import android.app.Application
import com.example.mymoviecatalogueapp.data.database.MyDatabase
import com.example.mymoviecatalogueapp.data.network.TheMovieDBApiService
import com.example.mymoviecatalogueapp.data.network.TheMovieDBDataSource
import com.example.mymoviecatalogueapp.data.network.TheMovieDBDataSourceImpl
import com.example.mymoviecatalogueapp.data.network.listMovie.MovieDataSourceFactory
import com.example.mymoviecatalogueapp.data.network.listMovie.TvShowDataSourceFactory
import com.example.mymoviecatalogueapp.repository.MyRepository
import com.example.mymoviecatalogueapp.repository.MyRepositoryImpl
import com.example.mymoviecatalogueapp.ui.detail.DetailsViewModelFactory
import com.example.mymoviecatalogueapp.ui.favorite.FavoriteHomeViewModelFactory
import com.example.mymoviecatalogueapp.ui.movie.ListMovieViewModelFactory
import com.example.mymoviecatalogueapp.ui.movies.tvshow.ListTvShowViewModelFactory
import com.example.mymoviecatalogueapp.ui.preferences.SettingPreferenceViewModelFactory
import com.example.mymoviecatalogueapp.ui.search.SearchHomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class MyApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { MyDatabase(instance()) }
        bind() from singleton { instance<MyDatabase>().movieDao() }
        bind() from singleton { instance<MyDatabase>().tvShowDao() }
        bind() from singleton { TheMovieDBApiService() }
        bind<TheMovieDBDataSource>() with singleton { TheMovieDBDataSourceImpl(instance()) }
        bind() from singleton { MovieDataSourceFactory(instance()) }
        bind() from singleton { TvShowDataSourceFactory(instance()) }
        bind<MyRepository>() with singleton {
            MyRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind() from provider {
            ListMovieViewModelFactory(
                instance()
            )
        }
        bind() from provider {
            ListTvShowViewModelFactory(
                instance()
            )
        }
        bind() from provider { FavoriteHomeViewModelFactory(instance()) }
        bind() from factory { id: Int -> DetailsViewModelFactory(instance(), id) }
        bind() from factory { query: String -> SearchHomeViewModelFactory(instance(), query) }
        bind() from factory { date: String -> SettingPreferenceViewModelFactory(instance(), date) }
    }

}