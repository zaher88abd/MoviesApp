package dev.zaherabd.moviesapp

import android.app.Application
import dev.zaherabd.moviesapp.di.AppComponent
import dev.zaherabd.moviesapp.di.AppModule
import dev.zaherabd.moviesapp.di.DaggerAppComponent

class MovieApp : Application() {
    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        initDaggerAppComponent()
    }

    private fun initDaggerAppComponent() {
        appComponent = DaggerAppComponent.factory().create(AppModule(this))
    }
}