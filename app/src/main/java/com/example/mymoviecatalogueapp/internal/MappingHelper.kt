package com.example.mymoviecatalogueapp.internal

import android.database.Cursor
import com.example.mymoviecatalogueapp.data.database.entity.*


object MappingHelper {

    fun mapCursorToArrayListMovie(cursor: Cursor): List<Movie> {
        val movieList = ArrayList<Movie>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_ID))
            val posterPath =
                cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_POSTER_PATH))
            val backdropPath =
                cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_BACKDROP_PATH))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_TITLE))
            val voteAverage =
                cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_VOTE_AVERAGE))
            val overview = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_OVERVIEW))
            val isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_IS_FAVORITE))
            movieList.add(
                Movie(
                    posterPath,
                    id,
                    backdropPath,
                    title,
                    voteAverage,
                    overview,
                    isFavorite
                )
            )
        }

        return movieList
    }

    fun mapCursorToArrayListTvShow(cursor: Cursor): List<TvShow> {
        val tvShowList = ArrayList<TvShow>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(TV_COLUMN_ID))
            val posterPath =
                cursor.getString(cursor.getColumnIndexOrThrow(TV_COLUMN_POSTER_PATH))
            val backdropPath =
                cursor.getString(cursor.getColumnIndexOrThrow(TV_COLUMN_BACKDROP_PATH))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(TV_COLUMN_NAME))
            val voteAverage =
                cursor.getDouble(cursor.getColumnIndexOrThrow(TV_COLUMN_VOTE_AVERAGE))
            val overview = cursor.getString(cursor.getColumnIndexOrThrow(TV_COLUMN_OVERVIEW))
            val isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(TV_COLUMN_IS_FAVORITE))
            tvShowList.add(
                TvShow(
                    name, posterPath, id, backdropPath, voteAverage, overview, isFavorite
                )
            )
        }

        return tvShowList
    }
//
//    fun mapCursorToObject(cursor: Cursor): Movie {
//        cursor.moveToFirst()
//        val id = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_ID))
//        val posterPath = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_POSTER_PATH))
//        val backdropPath =
//            cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_BACKDROP_PATH))
//        val title = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_TITLE))
//        val voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_VOTE_AVERAGE))
//        val overview = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_OVERVIEW))
//        val isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_COLUMN_IS_FAVORITE))
//        return Movie(posterPath, id, backdropPath, title, voteAverage, overview, isFavorite)
//    }

}