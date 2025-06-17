package com.mbialowas.moviehubspr_int_2025.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng


import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import com.mbialowas.moviehubspr_int_2025.BuildConfig
import com.mbialowas.moviehubspr_int_2025.mvvm.MapViewModel

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier
){
    val viewModel: MapViewModel = MapViewModel()
    val theaters by viewModel.theaters.collectAsState()
    val location = LatLng(49.839112, -97.211510) // San Francisco coordinates

    val api_key = BuildConfig.GOOGLE_API_KEY



    val context= LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)


    // initialize the google place api
    LaunchedEffect(Unit) {
        if (!Places.isInitialized()) {
            Places.initialize(context, api_key)
        }
        if (permissionState.status.isGranted) {
            val location = LatLng(49.839112, -97.211510) // Winnipeg coordinates
            viewModel.fetchNearbyTheaters(location, api_key)
            Log.i("Places", "Initialized")
        } else {
            permissionState.launchPermissionRequest()
        }
    }
    if(permissionState.status.isGranted){
        Map(viewModel = viewModel)
    }else{
        Text("Location permission is required to view  nearby theaters.")
    }

}

@Composable
fun Map(viewModel: MapViewModel){
    val winnipeg  = LatLng(49.839112, -97.211510) // Winnipeg coordinates
    var zoom by remember {mutableStateOf(10f)}

    val theatres by viewModel.theaters.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(winnipeg, zoom)
    }
    Log.i("theatres", theatres.toString())
    Column{
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.matchParentSize()
            ) {
                theatres.forEach { theater ->
                    val theaterLocation = LatLng(theater.latitude, theater.longitude)
                    Log.i("Marker", theaterLocation.toString())
                    Log.i("Marker", theater.name.toString())
                    Log.i("Marker", theater.address.toString())
                    Marker(
                        state = MarkerState(position = theaterLocation),
                        title = theater.name,
                        snippet = theater.address
                    )

                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.8f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        zoom += 1f
                        cameraPositionState.move(CameraUpdateFactory.zoomTo(zoom))
                    }
                ) {
                    Text("+")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        zoom -= 1f
                        cameraPositionState.move(CameraUpdateFactory.zoomTo(zoom))
                    }
                ){
                    Text("-")
                }
            }

        } // end map box
        Card(){
            var reviews by remember { mutableStateOf<List<String>>(emptyList()) }
            var showReviews by remember { mutableStateOf(false) }
            val context = LocalContext.current
            LazyColumn(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.9f))
                    .padding(8.dp)
            ) {
                items(theatres) { theater ->
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = theater.name)
                        Text(text = theater.address.toString())
                        Button(onClick = {
                            val gmmIntentUri = Uri.parse("google.navigation:q=${Uri.encode(theater.latitude.toString() + "," + theater.longitude.toString())}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                setPackage("com.google.android.apps.maps")
                            }
                            context.startActivity(mapIntent)
                        }) {
                            Text("Get Directions")
                        } // end get directions button
                        Button(onClick = {
                            showReviews = !showReviews
                            if (showReviews && reviews.isEmpty()) {
                                // Call ViewModel function to load reviews using theater.placeId
                                viewModel.loadReviews(theater.placeId.toString()) { fetchedReviews ->
                                    reviews = fetchedReviews
                                }
                            }
                        }) {
                            Text(if (showReviews) "Hide Reviews" else "View Reviews")
                        }

                        if (showReviews) {
                            reviews.forEach {
                                Text(text = "- $it", modifier = Modifier.padding(start = 8.dp))
                            }
                        }
                    }
                }
            }
        } // end card
    } // end column
} // end map