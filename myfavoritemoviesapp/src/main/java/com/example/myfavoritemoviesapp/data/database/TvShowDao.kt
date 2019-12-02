package com.example.mymoviecatalogueapp.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymoviecatalogueapp.data.database.entity.TvShow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tvShow: TvShow)

    @Query("select * from tv_show")
    fun selectAll(): LiveData<List<TvShow>>

    @Update
    fun update(tvShow: TvShow)

    @Query("select * from tv_show where id = :tvShowId")
    fun selectById(tvShowId: Int): LiveData<TvShow>

    @Query("select count(id) from tv_show where id = :tvShowId")
    fun countById(tvShowId: Int): Int

    @Query("select * from tv_show where isFavorite = 1")
    fun selectByFavorite(): LiveData<List<TvShow>>

    @Query("select * from tv_show where isFavorite = 1")
    fun selectByFavoriteforWidget(): List<TvShow>

    /* Cursor */

    @Query("select * from tv_show")
    fun selectAllForCursor(): Cursor

    @Query("select * from tv_show where isFavorite = 1")
    fun selectByFavoriteForCursor(): Cursor

}