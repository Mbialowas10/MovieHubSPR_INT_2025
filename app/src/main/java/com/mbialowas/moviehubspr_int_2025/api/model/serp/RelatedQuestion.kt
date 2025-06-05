package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedQuestion(
    @Json(name = "date")
    var date: String?,
    @Json(name = "displayed_link")
    var displayedLink: String?,
    @Json(name = "link")
    var link: String?,
    @Json(name = "list")
    var list: List<String?>?,
    @Json(name = "next_page_token")
    var nextPageToken: String?,
    @Json(name = "question")
    var question: String?,
    @Json(name = "serpapi_link")
    var serpapiLink: String?,
    @Json(name = "snippet")
    var snippet: String?,
    @Json(name = "source_logo")
    var sourceLogo: String?,
    @Json(name = "title")
    var title: String?
)