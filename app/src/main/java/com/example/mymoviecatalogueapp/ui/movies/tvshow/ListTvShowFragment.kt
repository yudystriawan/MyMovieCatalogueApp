package com.example.mymoviecatalogueapp.ui.movies.tvshow

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
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ListTvShowFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ListTvShowViewModelFactory by instance()

    private lateinit var viewModel: ListTvShowViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var textError: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_tv_show_fragment, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_tv_show)
        textError = view.findViewById(R.id.tv_error_list_tv)
        progressBar = view.findViewById(R.id.progress_bar_list_tv)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ListTvShowViewModel::class.java)

        initAdapter()
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val listAdapter =
            ListTvShowAdapter(
                glide,
                context!!
            )
        val gridLayoutManager = GridLayoutManager(context!!, 3)

        recyclerView.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = listAdapter
        }

        bindUI(listAdapter)
    }

    private fun bindUI(adapter: ListTvShowAdapter) = launch {

        viewModel.tvShowPagedList.observe(this@ListTvShowFragment, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(this@ListTvShowFragment, Observer {
            progressBar.visibility =
                toVisibility(viewModel.listIsEmpty() && it == NetworkState.LOADING)

            textError.visibility =
                toVisibility(viewModel.listIsEmpty() && it == NetworkState.ERROR)

            if (!viewModel.listIsEmpty()) {
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
