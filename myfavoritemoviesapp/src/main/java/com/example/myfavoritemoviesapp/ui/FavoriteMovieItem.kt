package com.example.mymoviecatalogueapp.ui.favorite

import com.example.myfavoritemoviesapp.R
import com.example.mymoviecatalogueapp.data.database.entity.Movie
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
        val posterPath = "https://image.tmdb.org/t/p/w342${movie.posterPath}"

        GlideApp
            .with(this.containerView)
            .load(posterPath)
            .into(img_poster)
    }


}