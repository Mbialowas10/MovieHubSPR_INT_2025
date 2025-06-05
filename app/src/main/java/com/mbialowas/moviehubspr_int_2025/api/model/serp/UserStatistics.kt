package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserStatistics(
    @Json(name = "platform")
    var platform: String?,
    @Json(name = "statistic")
    var statistic: String?
)