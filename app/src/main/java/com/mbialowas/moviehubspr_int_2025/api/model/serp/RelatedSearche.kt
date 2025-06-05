package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedSearche(
    @Json(name = "block_position")
    var blockPosition: Int?,
    @Json(name = "link")
    var link: String?,
    @Json(name = "query")
    var query: String?,
    @Json(name = "serpapi_link")
    var serpapiLink: String?
)