package com.mbialowas.moviehubspr_int_2025

import android.R.attr.title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubspr_int_2025.Navigation.BottomNav
import com.mbialowas.moviehubspr_int_2025.api.MovieManager
import com.mbialowas.moviehubspr_int_2025.destinations.Destination
import com.mbialowas.moviehubspr_int_2025.screens.MovieScreen
import com.mbialowas.moviehubspr_int_2025.screens.SearchScreen
import com.mbialowas.moviehubspr_int_2025.screens.WatchScreen
import com.mbialowas.moviehubspr_int_2025.ui.theme.MovieHubSPR_INT_2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubSPR_INT_2025Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val movieManager = MovieManager() // start the process to fetching api data
                    App(navController, modifier = Modifier.padding(innerPadding), movieManager)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier, movieManager: MovieManager){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MovieHub SPR INT 2025") }
            )
        },
        bottomBar = {
            BottomNav(navController = navController)
        }
    ){ paddingValues->
        paddingValues.calculateBottomPadding()
        Spacer(modifier = Modifier.padding(10.dp))
        NavHost(
            navController = navController as NavHostController,
            startDestination = Destination.Movie.route
        ){
            composable(Destination.Movie.route){
                MovieScreen(navController = navController, movieManager= movieManager)
            }
            composable(Destination.Search.route){
                SearchScreen()
            }
            composable(Destination.Watch.route){
                WatchScreen()
            }
        }
    }
}

