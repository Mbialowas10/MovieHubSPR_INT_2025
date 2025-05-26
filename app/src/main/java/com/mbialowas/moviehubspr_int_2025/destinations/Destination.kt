package com.mbialowas.moviehubspr_int_2025.destinations

sealed class Destination(val route: String) {
    object Movie : Destination("movie")
    object Search : Destination("search")
    object Watch : Destination("watch")
    object MovieDetail: Destination("movieDetail/{movieID}"){}
}