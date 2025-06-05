package com.mbialowas.moviehubspr_int_2025.api.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class ShowtimeResult(
    @Json(name="theater") val theater: Theater?,
    @Json(name="movie") val movie: Movie?,
    @Json(name="times") val times: List<String>?
)