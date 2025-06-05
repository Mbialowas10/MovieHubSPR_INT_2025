package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrganicResult(
    @Json(name = "date")
    var date: String?,
    @Json(name = "displayed_link")
    var displayedLink: String?,
    @Json(name = "favicon")
    var favicon: String?,
    @Json(name = "link")
    var link: String?,
    @Json(name = "position")
    var position: Int?,
    @Json(name = "redirect_link")
    var redirectLink: String?,
    @Json(name = "rich_snippet")
    var richSnippet: RichSnippet?,
    @Json(name = "snippet")
    var snippet: String?,
    @Json(name = "snippet_highlighted_words")
    var snippetHighlightedWords: List<String?>?,
    @Json(name = "source")
    var source: String?,
    @Json(name = "title")
    var title: String?
)