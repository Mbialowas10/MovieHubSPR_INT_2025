package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Theater(
    @Json(name = "address")
    var address: String?,
    @Json(name = "distance")
    var distance: String?,
    @Json(name = "link")
    var link: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "showing")
    var showing: List<Showing?>?
)