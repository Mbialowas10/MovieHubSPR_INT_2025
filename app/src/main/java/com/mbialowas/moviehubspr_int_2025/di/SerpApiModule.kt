package com.mbialowas.moviehubspr_int_2025.di

import com.mbialowas.moviehubspr_int_2025.api.SerpApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerpApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // add Kotlin support
            .build()

        return Retrofit.Builder()
            .baseUrl("https://serpapi.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideSerpApiService(retrofit: Retrofit): SerpApiService {
        return retrofit.create(SerpApiService::class.java)
    }
}
