package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Top(
    @Json(name = "detected_extensions")
    var detectedExtensions: DetectedExtensions?,
    @Json(name = "extensions")
    var extensions: List<String?>?
)