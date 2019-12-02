package com.example.mymoviecatalogueapp.data.database.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val TV_TABLE_NAME = "tv_show"
const val TV_COLUMN_ID = "id"
const val TV_COLUMN_POSTER_PATH = "posterPath"
const val TV_COLUMN_BACKDROP_PATH = "backdropPath"
const val TV_COLUMN_NAME = "name"
const val TV_COLUMN_OVERVIEW = "overview"
const val TV_COLUMN_VOTE_AVERAGE = "voteAverage"
const val TV_COLUMN_IS_FAVORITE = "isFavorite"

@Entity(tableName = TV_TABLE_NAME)
class TvShow(

    @ColumnInfo(name = TV_COLUMN_NAME)
    val name: String,

    @SerializedName("poster_path")
    @ColumnInfo(name = TV_COLUMN_POSTER_PATH)
    var posterPath: String? = null,

    @PrimaryKey
    @ColumnInfo(name = TV_COLUMN_ID)
    var id: Int,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = TV_COLUMN_BACKDROP_PATH)
    var backdropPath: String? = null,


    @SerializedName("vote_average")
    @ColumnInfo(name = TV_COLUMN_VOTE_AVERAGE)
    var voteAverage: Double,

    @ColumnInfo(name = TV_COLUMN_OVERVIEW)
    var overview: String,

    @ColumnInfo(name = TV_COLUMN_IS_FAVORITE)
    var isFavorite: Int = 0
)