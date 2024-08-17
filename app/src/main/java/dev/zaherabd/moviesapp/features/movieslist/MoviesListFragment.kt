package dev.zaherabd.moviesapp.features.movieslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.zaherabd.moviesapp.Constants.TAG
import dev.zaherabd.moviesapp.R
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
    private lateinit var moviesListAdapter: MoviesListAdapter

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
        moviesListAdapter = MoviesListAdapter()
        moviesListAdapter.onItemClick = { movie ->
            val bundle = Bundle()
            bundle.putSerializable("movie_id", movie.id)
            findNavController().navigate(R.id.show_movie_details, bundle)
        }
        binding.btnPopular.setOnClickListener {
            getPopularMovies()
        }
        binding.btnTopRated.setOnClickListener {
            getTopRatedMovies()
        }
        binding.btnNowPlaying.setOnClickListener {
            getNowPlaying()
        }

        binding.rvMoviesList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = moviesListAdapter
            Log.d(TAG, "onViewCreated: 2")
        }
    }

    private fun getPopularMovies() {
        val call: Call<APIResponse> = mService.getPopular()
        makeApiRequest(call)
    }

    private fun getTopRatedMovies() {
        val call: Call<APIResponse> = mService.getTopRated()
        makeApiRequest(call)
    }

    private fun getNowPlaying() {
        val call: Call<APIResponse> = mService.getNowPlaying()
        makeApiRequest(call)
    }

    private fun makeApiRequest(call: Call<APIResponse>) {
        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        moviesListAdapter.loadMoviesList(it)
                        moviesListAdapter.notifyDataSetChanged()
                    }

                } else {
                    Toast.makeText(context, "Error with getting data.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<APIResponse>, throwable: Throwable) {
                Toast.makeText(context, "Error with getting data.", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}