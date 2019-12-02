package com.example.myfavoritemovieapp


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val MOVIE_TABLE_NAME = "movie"
const val MOVIE_COLUMN_ID = "id"
const val MOVIE_COLUMN_POSTER_PATH = "posterPath"

@Entity(tableName = "movie")
data class Movie(
    @SerializedName("poster_path")
    @ColumnInfo(name = MOVIE_COLUMN_POSTER_PATH)
    val posterPath: String?,
    @PrimaryKey
    @ColumnInfo(name = MOVIE_COLUMN_ID)
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val overview: String,
    var isFavorite: Int = 0
)