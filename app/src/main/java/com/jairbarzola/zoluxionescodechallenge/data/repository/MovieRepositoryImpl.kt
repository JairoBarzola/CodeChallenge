package com.jairbarzola.zoluxionescodechallenge.data.repository

import com.jairbarzola.zoluxionescodechallenge.data.datasource.MovieDataSource
import com.jairbarzola.zoluxionescodechallenge.data.db.MovieDao
import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie
import com.jairbarzola.zoluxionescodechallenge.domain.repository.MovieRepository
import com.jairbarzola.zoluxionescodechallenge.domain.util.Failure
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType

class MovieRepositoryImpl(
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao
) : MovieRepository {
    override suspend fun getMovies(page: Int): ResultType<ListMovieResponse, Failure> =
        movieDataSource.getMovies(page)

    override suspend fun getMovies(): List<Movie> = movieDao.getMovies()

    override suspend fun insertAllMovies(movies: List<Movie>) {
        movies.forEach {
            movieDao.insertMovie(it)
        }
    }
}