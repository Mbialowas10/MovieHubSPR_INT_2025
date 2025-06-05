package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KnowledgeGraph(
    @Json(name = "audience_reviews")
    var audienceReviews: List<AudienceReview?>?,
    @Json(name = "budget")
    var budget: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "director")
    var director: String?,
    @Json(name = "director_links")
    var directorLinks: List<DirectorLink?>?,
    @Json(name = "editorial_reviews")
    var editorialReviews: List<EditorialReview?>?,
    @Json(name = "entity_type")
    var entityType: String?,
    @Json(name = "genre")
    var genre: String?,
    @Json(name = "genre_links")
    var genreLinks: List<GenreLink?>?,
    @Json(name = "kgmid")
    var kgmid: String?,
    @Json(name = "knowledge_graph_search_link")
    var knowledgeGraphSearchLink: String?,
    @Json(name = "mpaa_rating")
    var mpaaRating: String?,
    @Json(name = "mpaa_rating_links")
    var mpaaRatingLinks: List<MpaaRatingLink?>?,
    @Json(name = "producers")
    var producers: String?,
    @Json(name = "producers_links")
    var producersLinks: List<ProducersLink?>?,
    @Json(name = "release_date")
    var releaseDate: String?,
    @Json(name = "release_date_links")
    var releaseDateLinks: List<ReleaseDateLink?>?,
    @Json(name = "running_time")
    var runningTime: String?,
    @Json(name = "serpapi_knowledge_graph_search_link")
    var serpapiKnowledgeGraphSearchLink: String?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "type")
    var type: String?,
    @Json(name = "user_statistics")
    var userStatistics: UserStatistics?,
    @Json(name = "watch_now")
    var watchNow: List<WatchNow?>?
)