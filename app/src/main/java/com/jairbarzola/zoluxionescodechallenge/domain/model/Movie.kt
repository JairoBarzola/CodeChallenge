package com.jairbarzola.zoluxionescodechallenge.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val poster:String,
    val title:String,
    val voteAverage: Float,
    val releaseDate: String,
    val overview:String
)