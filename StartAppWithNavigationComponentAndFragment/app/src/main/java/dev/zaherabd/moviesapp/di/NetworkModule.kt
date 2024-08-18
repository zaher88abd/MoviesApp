package dev.zaherabd.moviesapp.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dev.zaherabd.moviesapp.Constants
import dev.zaherabd.moviesapp.network.MoviesCoroutineService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

//    @Provides
//    fun provideMovieService(): MoviesCallService {
//        val gson = GsonBuilder().create()
//        val retrofit = Retrofit.Builder()
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .baseUrl(Constants.BASE_URL)
//            .build()
//        return retrofit.create(MoviesCallService::class.java)
//    }

    @Provides
    fun provideMovieCoroutineService(): MoviesCoroutineService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
        return retrofit.create(MoviesCoroutineService::class.java)
    }
}