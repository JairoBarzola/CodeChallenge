package com.jairbarzola.zoluxionescodechallenge.domain.usecase

import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import com.jairbarzola.zoluxionescodechallenge.domain.repository.MovieRepository
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

class GetMoviesUseCase (private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page:Int): ResultType<ListMovieResponse, Failure> =
        movieRepository.getMovies(page)
}