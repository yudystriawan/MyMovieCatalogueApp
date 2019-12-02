package com.example.myfavoritemoviesapp.ui


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritemoviesapp.MappingHelper
import com.example.myfavoritemoviesapp.R
import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.example.mymoviecatalogueapp.data.database.entity.TvShow
import com.example.mymoviecatalogueapp.provider.MyProvider
import com.example.mymoviecatalogueapp.ui.favorite.FavoriteMovieItem
import com.example.mymoviecatalogueapp.ui.favorite.FavoriteTvShowItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteHomeFragment : Fragment() {

    companion object {
        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): FavoriteHomeFragment {
            val fragment =
                FavoriteHomeFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_home, container, false)
        progressBar = view.findViewById(R.id.progress_bar_favorite)
        recyclerView = view.findViewById(R.id.recycler_view_favorite)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            when (arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int) {
                1 -> loadMoviesAsync()
                2 -> loadTvShowAsync()
            }
        }
    }

    private fun loadTvShowAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val uriFavoriteTvShow = Uri.parse("${MyProvider.URI_TV_SHOW}/1")
            val deferred = async(Dispatchers.IO) {
                val cursor =
                    activity?.contentResolver?.query(
                        uriFavoriteTvShow,
                        null,
                        null,
                        null,
                        null,
                        null
                    ) as Cursor
                MappingHelper.mapCursorToArrayListTvShow(
                    cursor
                )
            }
            val tvShows = deferred.await()
            if (tvShows.isNotEmpty()) {
                initTvShowRecyclerView(tvShows.toTvShowItems())
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val uriFavoriteMovie = Uri.parse("${MyProvider.URI_MOVIE}/1")
            val deferred = async(Dispatchers.IO) {
                val cursor =
                    activity?.contentResolver?.query(
                        uriFavoriteMovie,
                        null,
                        null,
                        null,
                        null,
                        null
                    ) as Cursor
                MappingHelper.mapCursorToArrayListMovie(
                    cursor
                )
            }
            val movies = deferred.await()
            if (movies.isNotEmpty()) {
                initMovieRecyclerView(movies.toMovieItems())
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun List<Movie>.toMovieItems(): List<FavoriteMovieItem> {
        return this.map {
            FavoriteMovieItem(it)
        }
    }

    private fun initMovieRecyclerView(items: List<FavoriteMovieItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@FavoriteHomeFragment.context, 3)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as FavoriteMovieItem).let {
                showDetailsMovie(it.movie.id, view, true)
            }
        }

    }

    private fun List<TvShow>.toTvShowItems(): List<FavoriteTvShowItem> {
        return this.map {
            FavoriteTvShowItem(it)
        }
    }


    private fun initTvShowRecyclerView(items: List<FavoriteTvShowItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@FavoriteHomeFragment.context, 3)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as FavoriteTvShowItem).let {
                showDetailsMovie(it.tvShow.id, view, false)
            }
        }
    }


    private fun showDetailsMovie(id: Int, view: View, isMovie: Boolean) {
//        val intent = Intent(view.context, DetailsActivity::class.java)
//        intent.putExtra(DetailsActivity.EXTRA_ID, id)
//        intent.putExtra(DetailsActivity.IS_MOVIE, isMovie)
//        startActivity(intent)
    }


}
