package com.mbialowas.moviehubspr_int_2025.api.model

// Theater Info
data class ShowtimeTheater(
    val name: String?,
    val address: String?,
    val showing: List<Showtime>?
)