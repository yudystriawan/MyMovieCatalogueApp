package com.example.mymoviecatalogueapp.ui.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.network.BASE_POSTER_URL
import com.example.mymoviecatalogueapp.internal.glide.GlideRequests
import com.example.mymoviecatalogueapp.ui.detail.DetailsActivity

class MovieItemViewHolder(view: View, private val glide: GlideRequests) :
    RecyclerView.ViewHolder(view) {

    private val imgPoster = view.findViewById<ImageView>(R.id.img_poster)

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): MovieItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
            return MovieItemViewHolder(view, glide)
        }
    }

    fun bind(movie: Movie?, context: Context) {
        val posterPath = BASE_POSTER_URL + movie?.posterPath

        glide
            .load(posterPath)
            .fallback(R.drawable.ic_broken_image_grey_24dp)
            .into(imgPoster)

        itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.EXTRA_ID, movie?.id)
            intent.putExtra(DetailsActivity.IS_MOVIE, true)
            context.startActivity(intent)
        }
    }
}