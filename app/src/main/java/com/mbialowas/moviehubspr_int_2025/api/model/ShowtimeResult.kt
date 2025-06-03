package com.mbialowas.moviehubspr_int_2025.api.model

import com.google.gson.annotations.SerializedName

data class ShowtimeResult(
    @SerializedName("theater") val theater: Theater,
    @SerializedName("movie") val movie: Movie,
    @SerializedName("times") val times: List<String>
)