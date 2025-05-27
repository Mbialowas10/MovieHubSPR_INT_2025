package com.mbialowas.moviehubspr_int_2025

import android.R.attr.title
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubspr_int_2025.Navigation.BottomNav
import com.mbialowas.moviehubspr_int_2025.api.MovieManager
import com.mbialowas.moviehubspr_int_2025.api.db.AppDatabase
import com.mbialowas.moviehubspr_int_2025.api.model.Movie
import com.mbialowas.moviehubspr_int_2025.destinations.Destination
import com.mbialowas.moviehubspr_int_2025.mvvm.MovieViewModel
import com.mbialowas.moviehubspr_int_2025.screens.MovieDetailScreen
import com.mbialowas.moviehubspr_int_2025.screens.MovieScreen
import com.mbialowas.moviehubspr_int_2025.screens.SearchScreen
import com.mbialowas.moviehubspr_int_2025.screens.WatchScreen
import com.mbialowas.moviehubspr_int_2025.ui.theme.MovieHubSPR_INT_2025Theme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubSPR_INT_2025Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    // get db instance
                    val db = AppDatabase.getInstance(applicationContext)
                    val movieManager = MovieManager(db) // start the process to fetching api data
                    // initialize viewModel
                    val viewModel: MovieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
                    App(navController, modifier = Modifier.padding(innerPadding), movieManager, db,viewModel)
                }
            }
        }
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier, movieManager: MovieManager, db: AppDatabase,viewModel: MovieViewModel){
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
                MovieScreen(navController = navController, movieManager= movieManager, modifier = modifier, db = db)
            }
            composable(Destination.Search.route){
                SearchScreen(
                    modifier = Modifier.padding(paddingValues),
                    viewModel = viewModel,
                    database = db,
                    movieManager = movieManager,
                    navController = navController
                )
            }
            composable(Destination.Watch.route){
                WatchScreen()
            }
            composable(Destination.MovieDetail.route) {navBackStackEntry->
                var movie by remember{
                    mutableStateOf<Movie?>(null)
                }
                val movie_id: String? = navBackStackEntry.arguments?.getString("movieID")
                Log.i("MJB", movie_id.toString())
                GlobalScope.launch {
                    if (movie_id != null){
                        movie = db.movieDao().getMovieById(movie_id.toInt())
                    }
                }
                movie?.let{
                    MovieDetailScreen(modifier = Modifier.padding(paddingValues), movie=movie!!,db,navController,movieManager)

                }
                //MovieDetailScreen(modifier=modifier, movie_id)

            }
        }
    }
}

