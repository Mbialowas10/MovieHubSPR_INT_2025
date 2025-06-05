package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    @Json(name = "current")
    var current: Int?,
    @Json(name = "next")
    var next: String?,
    @Json(name = "other_pages")
    var otherPages: OtherPages?
)