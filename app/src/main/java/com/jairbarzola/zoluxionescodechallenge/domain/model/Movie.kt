package com.jairbarzola.zoluxionescodechallenge.domain.model

data class Movie(
    val id: String,
    val poster:String,
    val title:String,
    val voteAverage: Float,
    val releaseDate: String,
    val overview:String
)