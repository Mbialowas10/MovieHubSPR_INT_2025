package com.mbialowas.moviehubspr_int_2025.api.model.serp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OtherPages(
    @Json(name = "10")
    var x10: String?,
    @Json(name = "2")
    var x2: String?,
    @Json(name = "3")
    var x3: String?,
    @Json(name = "4")
    var x4: String?,
    @Json(name = "5")
    var x5: String?,
    @Json(name = "6")
    var x6: String?,
    @Json(name = "7")
    var x7: String?,
    @Json(name = "8")
    var x8: String?,
    @Json(name = "9")
    var x9: String?
)