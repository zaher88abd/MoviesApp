package dev.zaherabd.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.zaherabd.moviesapp.databinding.FragmentMoviesListBinding
import dev.zaherabd.moviesapp.di.NetworkModule
import dev.zaherabd.moviesapp.network.MoviesCallService
import dev.zaherabd.moviesapp.network.module.APIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null

    private lateinit var mService: MoviesCallService

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mService = NetworkModule().provideMovieService()
        binding.buttonSecond.setOnClickListener {
            val call: Call<APIResponse> = mService.getPopular()

            Log.d("TESTCode", "onViewCreated: get list ${call.request().url}")
            call.enqueue(object : Callback<APIResponse> {
                override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                    if (response.isSuccessful) {
                        Log.d("TESTCode", "onResponse: 1 ${response.body()?.results?.size}")
                    }else{
                        Log.d("TESTCode", "onResponse: 2 ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<APIResponse>, throwable: Throwable) {
                    Log.e("TESTCode", "onFailure: Error", throwable)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}