package com.mbialowas.moviehubspr_int_2025.mvvm

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
                showtimes = response.showtimes?.map {
                    SerpMovieShowtime(
                        movieName = it.movie.title.toString(),
                        theaterName = it.theater.name,
                        address = it.theater.address.toString(),
                        times = it.times
                    )
                } ?: emptyList()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
