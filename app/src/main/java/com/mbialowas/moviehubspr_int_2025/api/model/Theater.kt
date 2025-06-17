package com.mbialowas.moviehubspr_int_2025.api.model

import com.squareup.moshi.Json

data class Theater(
    @Json(name="name") val name:String,
    @Json(name="latitude") val latitude: Double,
    @Json(name="longitude") val longitude: Double,
    @Json(name="address") val address: String?,
    @Json(name="placeId") val placeId: String?
)
