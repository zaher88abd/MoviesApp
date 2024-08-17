package dev.zaherabd.moviesapp.features.movieslist

import android.widget.MultiAutoCompleteTextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.zaherabd.moviesapp.di.NetworkModule
import dev.zaherabd.moviesapp.network.MoviesCallService
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
    private var requestType = RequestType.POPULAR


    private var mService: MoviesCallService = NetworkModule().provideMovieService()

    fun getObserver(): MutableLiveData<List<MovieResponse>?> {
        return moviesList
    }

    fun updateRequestType(requestType: RequestType) {
        this.requestType = requestType
        makeCall()
    }

     fun makeCall() {
        val call: Call<APIResponse> = when (requestType) {
            RequestType.NOW_PLAYING -> {
                mService.getNowPlaying()
            }

            RequestType.POPULAR -> {
                mService.getPopular()
            }

            RequestType.TOP_RATED -> {
                mService.getTopRated()
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