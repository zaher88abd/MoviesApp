package dev.zaherabd.moviesapp.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterPath: String,
)