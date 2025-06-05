package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchMetadata(
    @Json(name = "created_at")
    var createdAt: String?,
    @Json(name = "google_url")
    var googleUrl: String?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "json_endpoint")
    var jsonEndpoint: String?,
    @Json(name = "processed_at")
    var processedAt: String?,
    @Json(name = "raw_html_file")
    var rawHtmlFile: String?,
    @Json(name = "status")
    var status: String?,
    @Json(name = "total_time_taken")
    var totalTimeTaken: Double?
)