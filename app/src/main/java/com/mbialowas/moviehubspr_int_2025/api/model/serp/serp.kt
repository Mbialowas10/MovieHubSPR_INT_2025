package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class serp(
    @Json(name = "available_on")
    var availableOn: List<AvailableOn>?,
    @Json(name = "knowledge_graph")
    var knowledgeGraph: KnowledgeGraph?,
    @Json(name = "organic_results")
    var organicResults: List<OrganicResult>?,
    @Json(name = "pagination")
    var pagination: Pagination?,
    @Json(name = "related_questions")
    var relatedQuestions: List<RelatedQuestion>?,
    @Json(name = "related_searches")
    var relatedSearches: List<RelatedSearche>?,
    @Json(name = "search_information")
    var searchInformation: SearchInformation?,
    @Json(name = "search_metadata")
    var searchMetadata: SearchMetadata?,
    @Json(name = "search_parameters")
    var searchParameters: SearchParameters?,
    @Json(name = "serpapi_pagination")
    var serpapiPagination: SerpapiPagination?,
    @Json(name = "showtimes")
    var showtimes: List<Showtime>?
)