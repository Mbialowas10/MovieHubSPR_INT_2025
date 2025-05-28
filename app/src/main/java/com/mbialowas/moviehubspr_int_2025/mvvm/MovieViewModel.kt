package com.mbialowas.moviehubspr_int_2025.mvvm


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbialowas.moviehubspr_int_2025.BuildConfig
import com.mbialowas.moviehubspr_int_2025.api.Api
import com.mbialowas.moviehubspr_int_2025.api.db.AppDatabase
import com.mbialowas.moviehubspr_int_2025.api.model.Movie
import com.mbialowas.moviehubspr_int_2025.api.model.MovieData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieViewModel : ViewModel() {
    // api key
    val apiKey: String = BuildConfig.TMDB_API_KEY
    // movies list
    val movies = mutableStateOf<List<Movie>>(emptyList())

    //variable to keep track to the movie icon state
    private val _movieIconState = MutableStateFlow<Map<Int,Boolean>>(emptyMap())
    val movieIconState = _movieIconState.asStateFlow()

    val searchTerm = mutableStateOf("")

    fun searchMovies(movieName:String, database: AppDatabase){

        // api call
        val service = Api.retrofitService.searchMovieByName(apiKey, movieName)

        service.enqueue(object : Callback<MovieData>{
            override fun onResponse(
                call: Call<MovieData?>,
                response: Response<MovieData?>
            ) {
                Log.i("SearchData","testing api call")
                movies.value = response.body()?.results ?: emptyList()
                Log.i("SeacrchData", movies.value.toString())
                GlobalScope.launch {
                    database.movieDao().insertAllMovies(movies.value)
                }
            }

            override fun onFailure(
                call: Call<MovieData?>,
                t: Throwable
            ) {
                Log.d("Error", "${t.message}")
            }

        })
    }



    /**
     *  Purpose - a function to update the move favourite state ie.
     *  true or false
     *  @param movieID: Int - represent the moveID
     *  @param database: AppDatabase - represents the database
     *  @return unit
     *  @excception - N/A
     */
    fun updateMovieIconState(movieId: Int, database: AppDatabase){
        viewModelScope.launch(Dispatchers.IO){
            val movie = database.movieDao().getMovieById(movieId)

            if ( movie != null ){
                movie.isFavourite = !movie.isFavourite

                // update movie in the database with new state
                launch(Dispatchers.IO) {
                    database.movieDao().updateMovieState(movie)

                    _movieIconState.value.toMutableMap().apply{
                        this[movieId] = movie.isFavourite
                    }
                }
            }else{
                Log.e("MovieViewModel", "Movie with ID $movieId not found in the database")
            }
        }
    }

}