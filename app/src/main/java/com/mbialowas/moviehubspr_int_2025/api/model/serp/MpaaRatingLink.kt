package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MpaaRatingLink(
    @Json(name = "link")
    var link: String?,
    @Json(name = "text")
    var text: String?
)