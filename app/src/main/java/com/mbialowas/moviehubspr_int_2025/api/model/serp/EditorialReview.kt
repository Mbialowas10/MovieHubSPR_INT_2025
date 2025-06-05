package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditorialReview(
    @Json(name = "link")
    var link: String?,
    @Json(name = "rating")
    var rating: String?,
    @Json(name = "title")
    var title: String?
)