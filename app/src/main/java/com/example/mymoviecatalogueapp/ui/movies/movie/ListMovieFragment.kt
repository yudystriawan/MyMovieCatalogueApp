package com.example.mymoviecatalogueapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.internal.glide.GlideApp
import com.example.mymoviecatalogueapp.repository.NetworkState
import com.example.mymoviecatalogueapp.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ListMovieFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ListMovieViewModelFactory by instance()

    private lateinit var viewModel: ListMovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var textError: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_movie_fragment, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_movie)
        textError = view.findViewById(R.id.tv_error_list_movie)
        progressBar = view.findViewById(R.id.progress_bar_list_movie)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ListMovieViewModel::class.java)

        initAdapter()

    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val listAdapter = ListMovieAdapter(glide, context!!)
        val gridLayoutManager = GridLayoutManager(context!!, 3)

        recyclerView.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = listAdapter
        }

        bindUI(listAdapter)
    }

    private fun bindUI(adapter: ListMovieAdapter) = launch {

        viewModel.moviePagedList.observe(this@ListMovieFragment, Observer {
            if (it == null) return@Observer
            adapter.submitList(it)
        })

        viewModel.networkState.observe(this@ListMovieFragment, Observer {
            progressBar.visibility =
                toVisibility(viewModel.isListEmpty() && it == NetworkState.LOADING)

            textError.visibility =
                toVisibility(viewModel.isListEmpty() && it == NetworkState.ERROR)

            if (!viewModel.isListEmpty()) {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun toVisibility(state: Boolean): Int {
        return if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
