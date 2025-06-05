package com.mbialowas.moviehubspr_int_2025.mvvm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.mbialowas.moviehubspr_int_2025.api.SerpApiService
import com.mbialowas.moviehubspr_int_2025.api.model.SerpMovieShowtime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowtimesViewModel @Inject constructor(
    private val serpApiService: SerpApiService
) : ViewModel() {

    // api key
    //var api_key: String = BuildConfig.SERP_API_KEY
    var showtimes by mutableStateOf<List<SerpMovieShowtime>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun fetchShowtimes(query: String, location: String, api_key: String) {

        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = serpApiService.getShowtimes(query, location, apiKey = api_key)
                Log.i("MJB", "Raw response: ${response.toString()}")
                Log.i("MJB", "Showtimes field: ${response.showtimes}")
                showtimes = response.showtimes?.mapNotNull {
                    val movieName = it.movie?.title
                    val theaterName = it.theater?.name
                    val address = it.theater?.address
                    val times = it.times

                    if (movieName != null && theaterName != null && address != null && times != null) {
                        SerpMovieShowtime(
                            movieName = movieName,
                            theaterName = theaterName,
                            address = address,
                            times = times
                        )
                    } else {
                        null // filter out incomplete records
                    }
                } ?: emptyList()
            } catch (e: Exception) {
                Log.e("ShowtimesViewModel", "API call failed", e)
                error = e.message
                showtimes = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}
