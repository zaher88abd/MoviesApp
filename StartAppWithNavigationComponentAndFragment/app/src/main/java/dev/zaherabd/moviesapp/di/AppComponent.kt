package dev.zaherabd.moviesapp.di

import android.app.Application
import dagger.Component
import dagger.Module
import dev.zaherabd.moviesapp.features.movieslist.MoviesListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }

    fun inject(moviesListFragment: MoviesListFragment)
}