package com.jairbarzola.zoluxionescodechallenge.domain.usecase

import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie
import com.jairbarzola.zoluxionescodechallenge.domain.repository.MovieRepository

class GetLocalMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> {
        return movieRepository.getMovies()
    }
}