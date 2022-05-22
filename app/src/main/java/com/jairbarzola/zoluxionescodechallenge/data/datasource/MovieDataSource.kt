package com.jairbarzola.zoluxionescodechallenge.data.datasource

import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

interface MovieDataSource {
    suspend fun getMovies(page:Int) : ResultType<ListMovieResponse, Failure>
}