package com.example.mymoviecatalogueapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.BASE_BACKDROP_URL
import com.example.mymoviecatalogueapp.data.network.BASE_POSTER_URL
import com.example.mymoviecatalogueapp.internal.glide.GlideApp
import com.example.mymoviecatalogueapp.ui.base.ScopedAppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory

class DetailsActivity : ScopedAppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory
            : ((Int) -> DetailsViewModelFactory) by factory()

    private lateinit var viewModel: DetailsViewModel

    companion object {
        const val EXTRA_ID = "extra_id"
        const val IS_MOVIE = "type_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val isMovie = intent.getBooleanExtra(IS_MOVIE, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory(id))
            .get(DetailsViewModel::class.java)

        if (isMovie) {
            bindDetailsMovieUI()
        } else {
            bindDetailsTvShowUI()
        }
    }

    private fun bindDetailsTvShowUI() = launch {
        val tvShow = viewModel.tvShow.await()

        tvShow.observe(this@DetailsActivity, Observer {
            if (it == null) return@Observer

            val posterPath = BASE_POSTER_URL + it.posterPath
            val backdropPath = BASE_BACKDROP_URL + it.backdropPath

            updateImages(posterPath, backdropPath)
            updateData(it.name, it.overview, it.voteAverage, it.isFavorite)

            val data = it
            img_favorite.setOnClickListener {
                updateFavorite(null, data)
            }

            progress_bar_details.visibility = View.GONE

        })
    }

    private fun bindDetailsMovieUI() = launch {
        val movie = viewModel.movie.await()

        movie.observe(this@DetailsActivity, Observer {
            if (it == null) return@Observer

            val posterPath = BASE_POSTER_URL + it.posterPath
            val backdropPath = BASE_BACKDROP_URL + it.backdropPath

            updateImages(posterPath, backdropPath)
            updateData(it.title, it.overview, it.voteAverage, it.isFavorite)

            val data = it
            img_favorite.setOnClickListener {
                updateFavorite(data, null)
            }

            progress_bar_details.visibility = View.GONE

        })

    }

    private fun updateFavorite(movie: Movie?, tvShow: TvShow?) = launch {
        if (movie != null) {
            when (movie.isFavorite) {
                1 -> {
                    movie.isFavorite = 0
                    tv_is_favorite.text = getString(R.string.title_favorite)
                    img_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                }
                0 -> {
                    movie.isFavorite = 1
                    tv_is_favorite.text = getString(R.string.title_favorited)
                    img_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                }
            }
            viewModel.updateMovie(movie)
        } else if (tvShow != null) {
            when (tvShow.isFavorite) {
                1 -> {
                    tvShow.isFavorite = 0
                    tv_is_favorite.text = getString(R.string.title_favorite)
                    img_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                }
                0 -> {
                    tvShow.isFavorite = 1
                    tv_is_favorite.text = getString(R.string.title_favorited)
                    img_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                }
            }
            viewModel.updateTvShow(tvShow)
        }
    }

    private fun updateImages(posterPath: String, backdropPath: String) {
        GlideApp
            .with(this@DetailsActivity)
            .load(posterPath)
            .into(img_poster)

        GlideApp
            .with(this@DetailsActivity)
            .load(backdropPath)
            .into(img_backdrop)
    }

    private fun updateData(
        title: String,
        overview: String,
        voteAverage: Double,
        favorite: Int
    ) {
        val vote = "$voteAverage/10"

        val textFavorite: String =
            if (favorite == 1) {
                img_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                getString(R.string.title_favorited)
            } else {
                img_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                getString(R.string.title_favorite)
            }

        tv_title.text = title
        tv_overview.text = overview
        tv_vote_average.text = vote
        tv_is_favorite.text = textFavorite
    }
}
