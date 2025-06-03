package com.mbialowas.moviehubspr_int_2025.api

import com.mbialowas.moviehubspr_int_2025.api.model.SerpApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SerpApiService {

    @GET("search")
    suspend fun getShowtimes(
        @Query("q") query: String,
        @Query("location") location: String,
        @Query("hl") language: String = "en",
        @Query("api_key") apiKey: String
    ): SerpApiResponse
}
