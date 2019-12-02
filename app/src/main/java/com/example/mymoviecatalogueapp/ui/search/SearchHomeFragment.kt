package com.example.mymoviecatalogueapp.ui.search

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
import com.example.mymoviecatalogueapp.ui.base.ListTvShowAdapter
import com.example.mymoviecatalogueapp.ui.base.ScopedFragment
import com.example.mymoviecatalogueapp.ui.movie.ListMovieAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class SearchHomeFragment : ScopedFragment(), KodeinAware {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_QUERY = "query"

        fun newInstance(index: Int, query: String): SearchHomeFragment {
            val fragment = SearchHomeFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            bundle.putString(ARG_QUERY, query)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val kodein by closestKodein()

    private val viewModelFactory: ((String) -> SearchHomeViewModelFactory) by factory()
    private lateinit var viewModel: SearchHomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var textError: TextView
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_home_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_Search)
        textError = view.findViewById(R.id.tv_error_Search)
        progressBar = view.findViewById(R.id.progress_bar_search)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var index = 1
        var query = ""
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            query = arguments?.getString(ARG_QUERY) as String
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory(query))
            .get(SearchHomeViewModel::class.java)

        initAdapter(index)
    }

    private fun initAdapter(index: Int) {
        val glide = GlideApp.with(this)
        val listMovieAdapter = ListMovieAdapter(glide, context!!)
        val listTvShowAdapter = ListTvShowAdapter(glide, context!!)
        val gridLayoutManager = GridLayoutManager(context!!, 3)

        recyclerView.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
        }

        when (index) {
            1 -> {
                bindUI(listMovieAdapter)
                recyclerView.adapter = listMovieAdapter
            }
            2 -> {
                bindTvShowUI(listTvShowAdapter)
                recyclerView.adapter = listTvShowAdapter
            }
        }

    }

    private fun bindTvShowUI(adapter: ListTvShowAdapter) {
        viewModel.tvShowPagedList.observe(this@SearchHomeFragment, Observer {
            if (it == null) return@Observer
            adapter.submitList(it)
        })

        viewModel.tvShowNetworkState.observe(this@SearchHomeFragment, Observer {
            progressBar.visibility =
                toVisibility(viewModel.isListTvShowEmpty() && it == NetworkState.LOADING)

            textError.visibility =
                toVisibility(viewModel.isListTvShowEmpty() && it == NetworkState.ERROR)

            if (!viewModel.isListTvShowEmpty()) {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun bindUI(adapter: ListMovieAdapter) {
        viewModel.moviePagedList.observe(this@SearchHomeFragment, Observer {
            if (it == null) return@Observer
            adapter.submitList(it)
        })

        viewModel.networkState.observe(this@SearchHomeFragment, Observer {
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
