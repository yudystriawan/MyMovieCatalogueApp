package com.example.mymoviecatalogueapp.ui.favorite

import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.network.BASE_POSTER_URL
import com.example.mymoviecatalogueapp.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item.*

class FavoriteMovieItem(
    val movie: Movie
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateImage()
        }
    }

    override fun getLayout(): Int = R.layout.item

    private fun ViewHolder.updateImage() {
        val posterPath = BASE_POSTER_URL + movie.posterPath

        GlideApp
            .with(this.containerView)
            .load(posterPath)
            .into(img_poster)
    }


}