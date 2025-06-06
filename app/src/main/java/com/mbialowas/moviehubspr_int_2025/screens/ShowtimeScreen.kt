package com.mbialowas.moviehubspr_int_2025.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.mbialowas.moviehubspr_int_2025.mvvm.ShowtimesViewModel
import com.mbialowas.moviehubspr_int_2025.BuildConfig
import com.mbialowas.moviehubspr_int_2025.api.model.SerpMovieShowtime

@Composable
fun ShowtimeScreen(
    modifier: Modifier,
    viewModel: ShowtimesViewModel,
    movieTitle:String
) {
    val showtimes = viewModel.showtimes
    val isLoading = viewModel.isLoading
    val error = viewModel.error
    val api_key= BuildConfig.SERP_API_KEY

    Log.d("SERP_API_KEY", api_key)

    LaunchedEffect(Unit) {
        viewModel.fetchShowtimes(
            query = movieTitle,
            location = "Winnipeg",
            api_key = api_key
        )
        Log.i("vm", showtimes.toString())
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
            MovieShowtimeList(modifier, movieTitle,showtimes)
            Log.i("ST", showtimes.toString())
        }
    }
}

@Composable
fun MovieShowtimeList(modifier: Modifier, movieTitle: String, showtimes: List<SerpMovieShowtime>) {
    Column(
        modifier = modifier
    ) {
        Text(text = "🎬 ${movieTitle}")
        LazyColumn {
            items(showtimes) { movie ->
                Card(modifier = Modifier.fillMaxSize()) {
                    Text(text = "📍 ${movie.theaterName}")
                    Text(text = "🏠 ${movie.address}")
                    Text(text = movie.day)
                    Text(text = "⏰ Showtimes: ${movie.times.joinToString(", ")}")
                }
                Spacer(
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

}

