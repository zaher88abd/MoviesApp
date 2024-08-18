package dev.zaherabd.moviesapp.features.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.zaherabd.moviesapp.di.NetworkModule
import dev.zaherabd.moviesapp.network.MoviesCallService
import dev.zaherabd.moviesapp.network.MoviesCoroutineService
import dev.zaherabd.moviesapp.network.module.APIResponse
import dev.zaherabd.moviesapp.network.module.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class RequestType {
    NOW_PLAYING, TOP_RATED, POPULAR
}

class MovieListViewModel : ViewModel() {

    private var moviesList = MutableLiveData<List<MovieResponse>?>()
    private var requestType = MutableLiveData<RequestType>()


    private var mService: MoviesCoroutineService = NetworkModule().provideMovieService()

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
        val call: Call<APIResponse> = when (requestType.value) {
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
                return
            }
        }
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if (response.isSuccessful) {
                    moviesList.postValue(response.body()?.results)
                } else {
                    moviesList.postValue(emptyList())
                }
            }

            override fun onFailure(p0: Call<APIResponse>, p1: Throwable) {
                moviesList.postValue(null)
            }
        })
    }


}