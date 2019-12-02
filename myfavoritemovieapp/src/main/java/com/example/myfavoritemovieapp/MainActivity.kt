package com.example.myfavoritemovieapp

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalogueapp.internal.glide.GlideApp
import com.example.mymoviecatalogueapp.internal.glide.GlideRequests
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val glide = GlideApp.with(this)

        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(glide)
        recycler_view.adapter = adapter

    }

    private class MovieAdapter(private val glideRequests: GlideRequests) :
        RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

        private lateinit var cursor: Cursor

        internal class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item, parent, false
            )
        ) {

            var imgPoster: ImageView = itemView.findViewById(R.id.img_poster)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return cursor.count
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (cursor.moveToPosition(position)) {
                val posterImage = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        MOVIE_COLUMN_POSTER_PATH
                    )
                )
                val posterPath = "https://image.tmdb.org/t/p/w34+$posterImage"
                glideRequests
                    .load(posterPath)
                    .into(holder.imgPoster)
            }
        }


    }
}
