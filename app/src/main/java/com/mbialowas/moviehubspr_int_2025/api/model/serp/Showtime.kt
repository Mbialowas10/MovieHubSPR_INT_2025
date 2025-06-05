package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Showtime(
    @Json(name = "day")
    var day: String?,
    @Json(name = "theaters")
    var theaters: List<Theater?>?
)