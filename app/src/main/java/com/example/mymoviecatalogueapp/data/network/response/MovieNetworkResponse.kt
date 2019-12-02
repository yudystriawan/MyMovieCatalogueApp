package com.example.mymoviecatalogueapp.data.network.response


import com.example.mymoviecatalogueapp.data.database.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieNetworkResponse(
    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<Movie>
)