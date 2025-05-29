package com.mbialowas.moviehubspr_int_2025

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mbialowas.moviehubspr_int_2025.screens.SignInScreen
import com.mbialowas.moviehubspr_int_2025.ui.theme.MovieHubSPR_INT_2025Theme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubSPR_INT_2025Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context: Context = applicationContext
                    SignInScreen(context = context, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

