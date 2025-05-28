package com.mbialowas.moviehubspr_int_2025.api

import com.mbialowas.moviehubspr_int_2025.api.model.MovieData
import retrofit2.Call

import retrofit2.http.GET

import retrofit2.http.Query

/** Purpose - query TMDB api for data
 *
 */
interface MovieService {

    @GET("trending/movie/day")
    fun getTrendingMovies(@Query("api_key") apiKey:String): Call<MovieData> // call to the parent

    @GET("search/movie")
    fun searchMovieByName(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : Call<MovieData>
}