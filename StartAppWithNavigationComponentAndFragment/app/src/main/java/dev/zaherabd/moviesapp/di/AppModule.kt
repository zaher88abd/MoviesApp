package dev.zaherabd.moviesapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule constructor(private val application: Application) {
    @Provides
    @Singleton
    fun getApplication(): Application {
        return application
    }
}