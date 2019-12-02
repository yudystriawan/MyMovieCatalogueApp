package com.example.mymoviecatalogueapp.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymoviecatalogueapp.data.database.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: Movie)

    @Query("select * from movie")
    fun selectAll(): LiveData<List<Movie>>

    @Update
    fun update(movie: Movie)

    @Query("select * from movie where id = :movieId")
    fun selectById(movieId: Int): LiveData<Movie>

    @Query("select count(id) from movie where id = :movieId")
    fun countById(movieId: Int): Int

    @Query("select * from movie where isFavorite = 1")
    fun selectByFavorite(): LiveData<List<Movie>>

    @Query("select * from movie where isFavorite = 1")
    fun selectByFavoriteForWidget(): List<Movie>

    /* Cursor */

    @Query("select * from movie")
    fun selectAllForCursor(): Cursor

    @Query("select * from movie where isFavorite = 1")
    fun selectByFavoriteForCursor(): Cursor

}