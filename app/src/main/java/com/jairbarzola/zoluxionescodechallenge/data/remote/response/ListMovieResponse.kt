package com.jairbarzola.zoluxionescodechallenge.data.remote.response

data class ListMovieResponse (
    val dates : LimitDate,
    val page: Int,
    val results: MutableList<MovieResponse>,
    val total_pages: Int,
    val total_results: Int

)

data class LimitDate (
    val maximum: String,
    val minimum: String
)

data class MovieResponse (
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: Array<Int>,
    val id: String,
    val original_language: String,
    val original_title: String,
    val overview:String,
    val popularity: String,
    val poster_path:String,
    val release_date:String,
    val title:String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
)