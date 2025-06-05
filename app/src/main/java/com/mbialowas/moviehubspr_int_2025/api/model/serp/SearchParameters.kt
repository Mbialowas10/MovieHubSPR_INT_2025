package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchParameters(
    @Json(name = "device")
    var device: String?,
    @Json(name = "engine")
    var engine: String?,
    @Json(name = "google_domain")
    var googleDomain: String?,
    @Json(name = "location_requested")
    var locationRequested: String?,
    @Json(name = "location_used")
    var locationUsed: String?,
    @Json(name = "q")
    var q: String?
)