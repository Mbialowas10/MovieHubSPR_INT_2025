package com.mbialowas.moviehubspr_int_2025.api.model

data class SerpMovieShowtime(
    val movieName: String,
    val theaterName: String,
    val address: String,
    val times: List<String>,
    val day: String
)
