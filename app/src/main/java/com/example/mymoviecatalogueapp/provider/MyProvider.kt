package com.example.mymoviecatalogueapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.mymoviecatalogueapp.data.database.MovieDao
import com.example.mymoviecatalogueapp.data.database.MyDatabase
import com.example.mymoviecatalogueapp.data.database.TvShowDao
import com.example.mymoviecatalogueapp.data.database.entity.MOVIE_TABLE_NAME
import com.example.mymoviecatalogueapp.data.database.entity.TV_TABLE_NAME

class MyProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.example.mymoviecatalogueapp.provider"

        val URI_MOVIE: Uri = Uri.parse("content://$AUTHORITY/$MOVIE_TABLE_NAME")
        val URI_TV_SHOW: Uri = Uri.parse("content://$AUTHORITY/$TV_TABLE_NAME")

        val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        lateinit var movieDao: MovieDao
        lateinit var tvShowDao: TvShowDao

        const val CODE_MOVIE_DIR = 1
        const val CODE_MOVIE_ITEM = 2

        const val CODE_TV_DIR = 3
        const val CODE_TV_ITEM = 4

        init {
            MATCHER.addURI(AUTHORITY, MOVIE_TABLE_NAME, CODE_MOVIE_DIR)
            MATCHER.addURI(AUTHORITY, "$MOVIE_TABLE_NAME/#", CODE_MOVIE_ITEM)

            MATCHER.addURI(AUTHORITY, TV_TABLE_NAME, CODE_TV_DIR)
            MATCHER.addURI(AUTHORITY, "$TV_TABLE_NAME/#", CODE_TV_ITEM)
        }
    }

    override fun onCreate(): Boolean {
        movieDao = MyDatabase(context as Context).movieDao()
        tvShowDao = MyDatabase(context as Context).tvShowDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        val cursor = when (MATCHER.match(uri)) {
            CODE_MOVIE_DIR -> movieDao.selectAllForCursor()
            CODE_MOVIE_ITEM -> movieDao.selectByFavoriteForCursor()
            CODE_TV_DIR -> tvShowDao.selectAllForCursor()
            CODE_TV_ITEM -> tvShowDao.selectByFavoriteForCursor()
            else -> null
        }

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
