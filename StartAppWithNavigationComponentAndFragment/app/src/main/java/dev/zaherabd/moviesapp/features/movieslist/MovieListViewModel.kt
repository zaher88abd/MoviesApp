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
                Log.d(TAG, "makeCall: 2")
                call
            }
            Log.d(TAG, "makeCall: 3 ${response.body()?.results}")
            moviesList.postValue(response.body()?.results)
        }

    }

}
