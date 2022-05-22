package com.jairbarzola.zoluxionescodechallenge.data.service

import com.jairbarzola.zoluxionescodechallenge.data.datasource.MovieDataSource
import com.jairbarzola.zoluxionescodechallenge.data.remote.ServiceFactory
import com.jairbarzola.zoluxionescodechallenge.data.remote.request.MovieRequest
import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

class MovieDataSourceImpl: MovieDataSource {
    override suspend fun getMovies(page: Int): ResultType<ListMovieResponse, Failure> {
        try {
            val service = ServiceFactory.createService(MovieRequest::class.java)
            service.getMovies(page = page).let {
                return if (it.isSuccessful && it.body() != null) {
                    ResultType.Success(it.body()!!)
                } else {
                    ResultType.Error(Failure(it.errorBody().toString()))
                }
            }
        } catch (e:Exception){
            return ResultType.Error(Failure(e.message))
        }
    }
}