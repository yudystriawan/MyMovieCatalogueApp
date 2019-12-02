package com.example.mymoviecatalogueapp.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalogueapp.R
import com.example.mymoviecatalogueapp.repository.NetworkState
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    fun bind(networkState: NetworkState?) {

        itemView.progress_bar_network.visibility =
            toVisibility(networkState == NetworkState.LOADED)

        itemView.tv_error_message_network.visibility =
            toVisibility(networkState == NetworkState.ERROR)
    }

    private fun toVisibility(state: Boolean): Int {
        return if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}