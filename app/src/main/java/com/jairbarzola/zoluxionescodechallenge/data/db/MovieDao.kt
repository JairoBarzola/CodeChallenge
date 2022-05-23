package com.jairbarzola.zoluxionescodechallenge.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Query("SELECT * FROM movie")
    fun getMovies(): List<Movie>
}