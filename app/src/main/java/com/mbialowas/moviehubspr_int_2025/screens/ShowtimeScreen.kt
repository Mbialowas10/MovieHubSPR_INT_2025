package com.mbialowas.moviehubspr_int_2025.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.mbialowas.moviehubspr_int_2025.mvvm.ShowtimesViewModel
import com.mbialowas.moviehubspr_int_2025.BuildConfig

@Composable
fun ShowtimeScreen(
    modifier: Modifier,
    viewModel: ShowtimesViewModel
) {
    val showtimes = viewModel.showtimes
    val isLoading = viewModel.isLoading
    val error = viewModel.error
    val api_key= BuildConfig.SERP_API_KEY

    LaunchedEffect(Unit) {
        viewModel.fetchShowtimes(
            query = "movie showtimes",
            location = "Winnipeg",
            api_key = api_key
        )
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${error}")
            }
        }
        else -> {
            //MovieShowtimeList(showtimes)
        }
    }
}
