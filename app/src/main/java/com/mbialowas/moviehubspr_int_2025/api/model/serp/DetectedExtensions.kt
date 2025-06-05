package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetectedExtensions(
    @Json(name = "rating")
    var rating: Double?,
    @Json(name = "reviews")
    var reviews: Int?
)