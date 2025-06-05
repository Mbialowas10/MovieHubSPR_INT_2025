package com.mbialowas.moviehubspr_int_2025.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api{

    private val BASE_URL = "https://api.themoviedb.org/3/"


    //tmdb
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //tmdb
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    //tmdb
    val retrofitService: MovieService by lazy{
        retrofit.create(MovieService::class.java)
    }

}