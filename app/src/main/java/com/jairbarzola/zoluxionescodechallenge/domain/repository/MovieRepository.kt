package com.jairbarzola.zoluxionescodechallenge.domain.repository

import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

interface MovieRepository {
    suspend fun getMovies(page:Int) :ResultType<ListMovieResponse,Failure>
}