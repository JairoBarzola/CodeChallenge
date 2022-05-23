package com.jairbarzola.zoluxionescodechallenge.data.repository

import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie
import com.jairbarzola.zoluxionescodechallenge.domain.repository.MovieRepository

class SaveMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movies: List<Movie>) {
        movieRepository.insertAllMovies(movies)
    }
}