package com.jairbarzola.zoluxionescodechallenge.data.remote.request

import com.jairbarzola.zoluxionescodechallenge.data.remote.URL
import com.jairbarzola.zoluxionescodechallenge.data.remote.response.ListMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRequest {
    @GET(URL.MOVIES)
    suspend fun getMovies(
        @Query("api_key") apiKey:String = URL.API_KEY,
        @Query("page") page:Int
    ) : Response<ListMovieResponse>
}