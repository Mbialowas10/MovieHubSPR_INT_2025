package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WatchNow(
    @Json(name = "image")
    var image: String?,
    @Json(name = "link")
    var link: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "serpapi_link")
    var serpapiLink: String?
)