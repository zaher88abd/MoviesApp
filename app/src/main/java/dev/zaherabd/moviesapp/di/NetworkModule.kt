package dev.zaherabd.moviesapp.di

import com.google.gson.GsonBuilder
import dev.zaherabd.moviesapp.Constants
import dev.zaherabd.moviesapp.network.MoviesCallService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    fun provideMovieService(): MoviesCallService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
        return retrofit.create(MoviesCallService::class.java)
    }
}