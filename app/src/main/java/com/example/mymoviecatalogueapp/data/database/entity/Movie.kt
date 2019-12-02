package com.example.mymoviecatalogueapp.data.database.entity


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

const val MOVIE_TABLE_NAME = "movie"
const val MOVIE_COLUMN_ID = "id"
const val MOVIE_COLUMN_POSTER_PATH = "posterPath"
const val MOVIE_COLUMN_BACKDROP_PATH = "backdropPath"
const val MOVIE_COLUMN_TITLE = "title"
const val MOVIE_COLUMN_OVERVIEW = "overview"
const val MOVIE_COLUMN_VOTE_AVERAGE = "voteAverage"
const val MOVIE_COLUMN_IS_FAVORITE = "isFavorite"

@Parcelize
@Entity(tableName = MOVIE_TABLE_NAME)
class Movie(

    @SerializedName("poster_path")
    @ColumnInfo(name = MOVIE_COLUMN_POSTER_PATH)
    var posterPath: String? = null,

    @PrimaryKey
    @ColumnInfo(name = MOVIE_COLUMN_ID)
    var id: Int = 0,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = MOVIE_COLUMN_BACKDROP_PATH)
    var backdropPath: String? = null,

    @ColumnInfo(name = MOVIE_COLUMN_TITLE)
    var title: String,

    @SerializedName("vote_average")
    @ColumnInfo(name = MOVIE_COLUMN_VOTE_AVERAGE)
    var voteAverage: Double,

    @ColumnInfo(name = MOVIE_COLUMN_OVERVIEW)
    var overview: String,

    @ColumnInfo(name = MOVIE_COLUMN_IS_FAVORITE)
    var isFavorite: Int = 0

) : Parcelable