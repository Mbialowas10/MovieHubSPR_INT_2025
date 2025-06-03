package com.mbialowas.moviehubspr_int_2025.api.model

import com.google.gson.annotations.SerializedName

data class SerpApiResponse(
    @SerializedName("showtimes") val showtimes: List<ShowtimeResult>?
)