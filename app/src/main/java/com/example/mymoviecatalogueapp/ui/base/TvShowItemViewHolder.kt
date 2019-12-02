package com.example.mymoviecatalogueapp.ui.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.data.network.BASE_POSTER_URL
import com.example.mymoviecatalogueapp.internal.glide.GlideRequests
import com.example.mymoviecatalogueapp.ui.detail.DetailsActivity

class TvShowItemViewHolder(view: View, private val glide: GlideRequests) :
    RecyclerView.ViewHolder(view) {

    private val imgPoster = view.findViewById<ImageView>(R.id.img_poster)

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): TvShowItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
            return TvShowItemViewHolder(
                view,
                glide
            )
        }
    }

    fun bind(tvShow: TvShow?, context: Context) {
        val posterPath = BASE_POSTER_URL + tvShow?.posterPath

        glide
            .load(posterPath)
            .into(imgPoster)

        itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.EXTRA_ID, tvShow?.id)
            context.startActivity(intent)
        }
    }
}