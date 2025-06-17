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
        var query_revised = "$query movie showtimes".toString()
        Log.i("MJb", query_revised)
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = serpApiService.getShowtimes(query_revised, location, apiKey = api_key)
                Log.i("MJB", "Raw response: ${response.toString()}")
                Log.i("MJB", "Showtimes field: ${response.showtimes}")
                showtimes = response.showtimes?.flatMap { dayEntry ->
                    dayEntry.theaters?.mapNotNull { theater ->
                        val times = theater.showing?.flatMap { it.time ?: emptyList() } ?: emptyList()
                        if (theater.name != null && theater.address != null && times.isNotEmpty()) {
                            SerpMovieShowtime(
                                movieName = query, // hardcoded for now
                                theaterName = theater.name,
                                address = theater.address,
                                times = times,
                                day =  dayEntry.day.toString()
                            )
                        } else null
                    } ?: emptyList()
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
