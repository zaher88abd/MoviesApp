package dev.zaherabd.moviesapp.features.movieslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zaherabd.moviesapp.Constants.TAG
import dev.zaherabd.moviesapp.di.NetworkModule
import dev.zaherabd.moviesapp.network.MoviesCallService
import dev.zaherabd.moviesapp.network.MoviesCoroutineService
import dev.zaherabd.moviesapp.network.module.APIResponse
import dev.zaherabd.moviesapp.network.module.MovieResponse
import dev.zaherabd.moviesapp.repository.AppDatabase
import dev.zaherabd.moviesapp.repository.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

enum class RequestType {
    NOW_PLAYING, TOP_RATED, POPULAR
}

class MovieListViewModel @Inject constructor() : ViewModel() {

    private var moviesList = MutableLiveData<List<MovieResponse>?>()
    private var requestType = MutableLiveData<RequestType>()


    @Inject
    lateinit var mService: MoviesCoroutineService

    @Inject
    lateinit var database: AppDatabase


    init {
        requestType.postValue(RequestType.NOW_PLAYING)
    }

    fun getRequestTypeObserver(): MutableLiveData<RequestType> {
        return requestType
    }

    fun getMoviesListObserver(): MutableLiveData<List<MovieResponse>?> {
        return moviesList
    }

    fun updateRequestType(requestType: RequestType) {
        this.requestType.postValue(requestType)
        makeCall()
    }

    fun makeCall() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                Log.d(TAG, "makeCall: 1")
                val call: Response<APIResponse> = when (requestType.value) {
                    RequestType.NOW_PLAYING -> {
                        mService.getNowPlaying()
                    }

                    RequestType.POPULAR -> {
                        mService.getPopular()
                    }

                    RequestType.TOP_RATED -> {
                        mService.getTopRated()
                    }

                    else -> {
                        mService.getNowPlaying()
                    }
                }
                call
            }
            moviesList.postValue(response.body()?.results)
        }

    }

    fun addMovieToUserList(movie: MovieResponse) {
        viewModelScope.launch {
            database.moviesDao().insertMovie(
                movie = MovieEntity(
                    movie.id,
                    movie.title,
                    movie.releaseDate,
                    movie.voteAverage,
                    movie.posterPath
                )
            )
        }
    }

}
