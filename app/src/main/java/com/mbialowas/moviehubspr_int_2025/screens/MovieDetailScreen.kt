package com.mbialowas.moviehubspr_int_2025.screens


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mbialowas.moviehubspr_int_2025.api.MovieManager
import com.mbialowas.moviehubspr_int_2025.api.db.AppDatabase
import com.mbialowas.moviehubspr_int_2025.api.model.Movie
import com.mbialowas.moviehubspr_int_2025.destinations.Destination
import com.mbialowas.moviehubspr_int_2025.mvvm.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Dispatcher

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MovieDetailScreen(
    modifier: Modifier, movie: Movie,
    db: AppDatabase,
    navController: NavController,
    movieManager: MovieManager,
    viewModel: MovieViewModel,
    fs_db: FirebaseFirestore
){
    Box(
        modifier
            .background(Color.Black)
            .fillMaxSize()
    ){
        Log.i("MovieDetailScreen","${movie.title}")
        Log.i("MovieDetailScreen","${movie.overview}")
        Log.i("MovieDetailScreen","${movie.posterPath}")

        // state level variable to track movie favourite state
        val iconState by viewModel.movieIconState.collectAsState() // observe state/data from viewmodel
        var isIconChanged = iconState[movie.id] ?: false // get the latest state/date for given movie
        var showEditDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by  remember { mutableStateOf(false) }
        var lastInsertedDocument: DocumentReference? by remember { mutableStateOf<DocumentReference?>(null) }

        Column{
            Text(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(),
                text = "Movie Detail Screen",
                style = MaterialTheme.typography.headlineMedium,
                color=Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            // Image Box with overlaid buttons similar to
            // a div in web dev
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ){
                AsyncImage(
                    modifier = Modifier
                        .matchParentSize(),
                    model= ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                        .build(),
                    contentDescription = movie.overview,
                    contentScale = ContentScale.FillBounds
                )
                IconButton(
                    onClick = {
                        isIconChanged = !isIconChanged
                        viewModel.updateMovieIconState(movie.id!!,db)
                        // fb interaction
                        var movieExists: Boolean? = null

                        // firebase db
                        val collection: CollectionReference = fs_db.collection("movies")
                        val m = hashMapOf(
                            "movie_id" to "${movie.id}",
                            "movie_title" to "${movie.title}",
                            "movie_overview" to "${movie.overview}",
                            "movie_poster_path" to "${movie.posterPath}",
                            "movie_release_date" to "${movie.releaseDate}",
                            "movie_popularity" to "${movie.popularity}",
                            "movie_vote_average" to "${movie.voteAverage}",
                            "movie_vote_count" to "${movie.voteCount}",
                            "movie_is_favourite" to "${movie.isFavorite}"
                        )
                        GlobalScope.launch {
                            movieExists = doesMovieExist(movie.id.toString(), collection)

                            // add a new movie to movies collection
                            if (isIconChanged && movieExists == false) {
                                fs_db.collection("movies").add(m)
                                    .addOnSuccessListener { documentReference ->
                                        lastInsertedDocument = documentReference
                                        Log.d(
                                            "FS",
                                            "DocumentSnapShot add with ID: ${documentReference.id}"
                                        )
                                    } // end addOnSuccessListener
                                    .addOnFailureListener { e ->
                                        Log.w("FS", "Error adding document", e)
                                    }// end addOnFailureListener
                            } else if( isIconChanged == false && movieExists == true) {
                                lastInsertedDocument?.delete()
                                    ?.addOnSuccessListener {
                                        Log.i("Removal", "${movie.title} removed from Firestore Db.")
                                    }?.addOnFailureListener {
                                        Log.i(
                                            "Removal",
                                            "THERE. WAS A PROBLEM REMOVING ${movie.title} FROM FireStore. Please call Mark Zuckerberg!"
                                        )
                                    }
                            }



                    }

                              },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha=0.6f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isIconChanged) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Watch Later",
                        tint=Color.White
                    )
                }
                // Edit/Delete Button
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Button(
                        onClick = {
                            showEditDialog = true
                        }
                    ) {
                        Text("Edit")
                    }
                    Button(
                        onClick = {
                            showDeleteDialog =true
                        }
                    ) {
                        Text("Delete")
                    }
                    Button(
                        onClick = {
                            navController.navigate("showtimeScreen/${movie.title}")
                        }
                    ){
                        Text("View ShowTimes...")
                    }
                    // show Dialogs
                    if (showDeleteDialog){
                        DeleteMovieDialog(
                            movie = movie,
                            onDismiss = {showDeleteDialog = false},
                            onConfirmDelete = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    // call our Movie dao
                                    db.movieDao().delete(movie)
                                    Log.i("MJB", "Past delete BP!")

                                    //navController.navigate(Destination.Movie.route)
                                    // navigation must take place on mainThread not a background
                                    // running thread aka coroutine.
                                    // refresh the movie screen
                                    movieManager.refreshMovies()

                                }
                                showDeleteDialog = false
                                navController.navigate(Destination.Movie.route)
                            }
                        )
                    } // end DeleteDialog
                    if (showEditDialog){
                        EditMovieDialog(
                            movie = movie,
                            onDismiss = { showEditDialog = false },
                            onConfirmEdit =  {newTitle, newDescription ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    movie.id?.let{movie->
                                        // update movie in database
                                        db.movieDao().updateMovie(movie,newTitle,newDescription)
                                        Log.i("MJB", "Past edit BP!")
                                        movieManager.refreshMovies()
                                    }
                                }
                                showEditDialog = false
                                navController.navigate(Destination.Movie.route)

                            }


                        )
                    }
                } // end of Edit/Delete row

            } // end of the image box
            Column(
                Modifier.padding(20.dp)
            ){
                Spacer(
                    modifier = Modifier.padding(5.dp))

                    movie.releaseDate?.let{
                        Text(
                            text = "Release Date: $it",
                            modifier = Modifier.padding(end=5.dp),
                            maxLines = 1,
                            fontSize = 12.sp,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        movie.overview?.let {
                            Text(
                                text= it,
                                modifier = Modifier.padding(end=8.dp),
                                maxLines = 3,
                                fontSize = 16.sp,
                                overflow= TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium,
                                color=Color.White
                            )
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Row {
                            movie.voteAverage?.let {
                                Text(
                                    text = "Avg Vote: $it",
                                    modifier = Modifier.padding(end = 8.dp),
                                    maxLines = 1,
                                    fontSize = 20.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White
                                )
                            }
                            movie.voteCount?.let {
                                Text(
                                    text = "# of votes: $it",
                                    modifier = Modifier.padding(end = 8.dp),
                                    maxLines = 1,
                                    fontSize = 20.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White
                                )
                            }
                        }
                    }
            }
        }
    }
}

suspend fun doesMovieExist(
    movieID: String,
    collection: CollectionReference
): Boolean? {
    val querySnapshot = collection.whereEqualTo("movie_id", movieID).get().await()
    return !querySnapshot.isEmpty
}
