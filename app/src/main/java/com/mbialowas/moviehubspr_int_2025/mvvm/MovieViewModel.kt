package com.mbialowas.moviehubspr_int_2025.mvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mbialowas.moviehubspr_int_2025.BuildConfig
import com.mbialowas.moviehubspr_int_2025.api.Api
import com.mbialowas.moviehubspr_int_2025.api.db.AppDatabase
import com.mbialowas.moviehubspr_int_2025.api.model.Movie
import com.mbialowas.moviehubspr_int_2025.api.model.MovieData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    // api key
    var api_key: String = BuildConfig.TMDB_API_KEY
    //search term
    val searchTerm = mutableStateOf("")
    // movies list
    val movies = mutableStateOf<List<Movie>>(emptyList())

    fun searchMovies(movieName:String, database: AppDatabase){

        // api call
        val service = Api.retrofitService.searchMovieByName(api_key, movieName)

        service.enqueue(object : Callback<MovieData> {
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                Log.i("SearchDta","testing api call")
                movies.value = response.body()?.results ?: emptyList()
                Log.i("SearchData", movies.value.toString())
                GlobalScope.launch{
                    database.movieDao().insertAllMovies(movies.value)
                }

            }

            override fun onFailure(
                call: Call<MovieData>,
                t: Throwable
            ) {
                Log.d("Error", "${t.message}")
            }

        })





    }

}