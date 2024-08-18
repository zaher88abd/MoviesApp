package dev.zaherabd.moviesapp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {
    @Query("Select * from movies")
    suspend fun getMovies():List<MovieEntity>

    @Insert
    suspend fun insertMovie(movie:MovieEntity)

    @Insert
    suspend fun deleteMovie(movie: MovieEntity)
}
