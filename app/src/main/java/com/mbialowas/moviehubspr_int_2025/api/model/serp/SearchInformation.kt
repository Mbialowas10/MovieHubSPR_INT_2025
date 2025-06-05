package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchInformation(
    @Json(name = "organic_results_state")
    var organicResultsState: String?,
    @Json(name = "query_displayed")
    var queryDisplayed: String?,
    @Json(name = "time_taken_displayed")
    var timeTakenDisplayed: Double?,
    @Json(name = "total_results")
    var totalResults: Int?
)