package com.jairbarzola.zoluxionescodechallenge.presentation.home

import com.jairbarzola.zoluxionescodechallenge.domain.model.Movie

sealed class HomeResult {
    object ShowLoading : HomeResult()
    data class ShowMovies(var movies: List<Movie>) : HomeResult()
    data class ShowError(val error: String) : HomeResult()
}