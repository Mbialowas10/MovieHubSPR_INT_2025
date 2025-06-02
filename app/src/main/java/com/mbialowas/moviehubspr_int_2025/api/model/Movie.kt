package com.mbialowas.moviehubspr_int_2025.api.model


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "movies")
data class Movie(


    //@Json(name = "genre_ids")
    //var genreIds: List<Int?>?,
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    var id: Int?,
    @Json(name = "overview")
    var overview: String?,
    @Json(name = "popularity")
    var popularity: Double?,
    @Json(name = "poster_path")
    var posterPath: String?,
    @Json(name = "release_date")
    var releaseDate: String?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "vote_average")
    var voteAverage: Double?,
    @Json(name = "vote_count")
    var voteCount: Int?,
    var isFavorite: Boolean = false

)