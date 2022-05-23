package com.jairbarzola.zoluxionescodechallenge.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jairbarzola.zoluxionescodechallenge.data.mapper.toModel
import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import com.jairbarzola.zoluxionescodechallenge.data.repository.SaveMoviesUseCase
import com.jairbarzola.zoluxionescodechallenge.domain.usecase.GetLocalMoviesUseCase
import com.jairbarzola.zoluxionescodechallenge.domain.usecase.GetMoviesUseCase
import com.jairbarzola.zoluxionescodechallenge.domain.util.ResultType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val saveMoviesUseCase: SaveMoviesUseCase,
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase
) : ViewModel() {
    private val _viewStateMutable = MutableLiveData<HomeResult>()
    val viewStateMutable: LiveData<HomeResult> = _viewStateMutable

    var movieResponse: ListMovieResponse? = null
    var isLastPage = false

    fun getMovies(page: Int) {
        viewModelScope.launch(IO) {
            _viewStateMutable.postValue(HomeResult.ShowLoading)
            when (val result = getMoviesUseCase.invoke(page)) {
                is ResultType.Success -> {
                    saveMoviesUseCase.invoke(result.value.results.map { it.toModel() })
                    _viewStateMutable.postValue(handleMoviesResponse(page, result.value))
                }
                is ResultType.Error -> {
                    _viewStateMutable.postValue(HomeResult.ShowError("No se pudo obtener las peliculas."))
                }
            }
        }
    }

    fun getMovies(){
        viewModelScope.launch(IO){
            _viewStateMutable.postValue(HomeResult.ShowMovies(getLocalMoviesUseCase.invoke()))
        }
    }

    private fun handleMoviesResponse(page: Int, result: ListMovieResponse?): HomeResult {
        isLastPage = result?.total_pages == page
        if (movieResponse == null) {
            movieResponse = result
        } else {
            val oldTransactions = movieResponse?.results
            val newTransactions = result?.results
            newTransactions?.let { oldTransactions?.addAll(it) }
        }
        return HomeResult.ShowMovies(movieResponse?.results?.map {
            it.toModel()
        } ?: result?.results?.map {
            it.toModel()
        }!!)
    }

}