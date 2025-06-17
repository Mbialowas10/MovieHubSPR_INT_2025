package com.mbialowas.moviehubspr_int_2025.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mbialowas.moviehubspr_int_2025.BuildConfig

import com.mbialowas.moviehubspr_int_2025.api.model.Theater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlinx.coroutines.withContext

class MapViewModel : ViewModel() {
    private val _theaters = MutableStateFlow<List<Theater>>(emptyList())
    val theaters: StateFlow<List<Theater>> get() = _theaters

    private val client = OkHttpClient()

    fun fetchNearbyTheaters(location: LatLng, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO){
            val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=${location.latitude},${location.longitude}" +
                    "&radius=10000" + // within 10km radius
                    "&type=movie_theater" +
                    "&key=$apiKey"
            Log.i("URL", url)
            val request = Request.Builder().url(url).build()

            try{
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null){
                    val theaterList = parseTheaterResponse(responseBody)
                    _theaters.value = theaterList
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    suspend fun fetchReviews(placeId: String, apiKey: String): List<String> {
        return withContext(Dispatchers.IO) {
            val url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=$placeId&fields=reviews&key=$apiKey"
            val request = Request.Builder().url(url).build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (!response.isSuccessful || responseBody == null) {
                    return@withContext emptyList<String>()
                }

                val json = JSONObject(responseBody)
                val reviews = mutableListOf<String>()

                json.optJSONObject("result")?.optJSONArray("reviews")?.let { reviewArray ->
                    for (i in 0 until reviewArray.length()) {
                        val review = reviewArray.getJSONObject(i)
                        val text = review.optString("text")
                        reviews.add(text)
                    }
                }
                return@withContext reviews
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext emptyList()
            }
        }
    }

    fun loadReviews(placeId: String, callback: (List<String>) -> Unit) {
        viewModelScope.launch {
            val reviews = fetchReviews(placeId, BuildConfig.GOOGLE_API_KEY)
            callback(reviews)
        }
    }


}
private fun parseTheaterResponse(response: String): List<Theater>{
    val theaters = mutableListOf<Theater>()
    val jsonObject = JSONObject(response)
    val resultsArray = jsonObject.getJSONArray("results")
    for (i in 0 until resultsArray.length()){
        val place = resultsArray.getJSONObject(i)
        val name = place.optString("name", "Unknown Theater")
        val address = place.optString("vicinity", "Unkown Address")
        val location = place.getJSONObject("geometry").getJSONObject("location")
        val latitude = location.getDouble("lat")
        val longitude = location.getDouble("lng")
        val placeId = place.optString("place_id","")

        theaters.add(Theater(name,latitude,longitude,address, placeId = placeId))
    }
    return theaters
}