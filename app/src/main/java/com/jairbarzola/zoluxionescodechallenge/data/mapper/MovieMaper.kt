package com.jairbarzola.zoluxionescodechallenge.data.mapper

import com.jairbarzola.zoluxionescodechallenge.data.remote.URL.PATH_IMAGE_URL
import com.jairbarzola.zoluxionescodechallenge.data.remote.response.MovieResponse
import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie


fun MovieResponse.toModel()
= Movie(
    id = id,
    poster =  PATH_IMAGE_URL+poster_path,
    title = title,
    voteAverage =  vote_average,
    overview = overview,
    releaseDate = release_date
)