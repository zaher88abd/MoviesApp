package dev.zaherabd.moviesapp.features.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.zaherabd.moviesapp.databinding.FragmentMoviesListBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null

    private lateinit var moviesListViewModule: MovieListViewModel

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
        initViewModel()
        initRV()
        binding.btnPopular.setOnClickListener {
            getPopularMovies()
        }
        binding.btnTopRated.setOnClickListener {
            getTopRatedMovies()
        }
        binding.btnNowPlaying.setOnClickListener {
            getNowPlaying()
        }

    }

    private fun initViewModel() {
        moviesListViewModule =
            ViewModelProvider(this)[MovieListViewModel::class.java]
        moviesListViewModule.getMoviesListObserver()
            .observe(this@MoviesListFragment.viewLifecycleOwner) { moviesList ->
                if (moviesList == null) {
                    showToastMsg("Sorry error getting movies list.")
                } else if (moviesList.isEmpty()) {
                    showToastMsg("Wrong option nothing to show.")
                } else {
                    moviesListAdapter.loadMoviesList(moviesList)
                    moviesListAdapter.notifyDataSetChanged()
                }
            }
        moviesListViewModule.getRequestTypeObserver()
            .observe(this@MoviesListFragment.viewLifecycleOwner) { requireType ->
                binding.tvMovieListType.text = requireType.name
            }
        moviesListViewModule.makeCall()
    }

    private fun showToastMsg(msg: String) {

    }

    private fun initRV() {
        moviesListAdapter = MoviesListAdapter()
        moviesListAdapter.onItemClick =
            { movie ->
                val action = MoviesListFragmentDirections.showMovieDetails(movie)
                findNavController().navigate(action)
            }
        binding.rvMoviesList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = moviesListAdapter
        }
    }

    private fun getPopularMovies() {
        moviesListViewModule.updateRequestType(RequestType.POPULAR)
    }

    private fun getTopRatedMovies() {
        moviesListViewModule.updateRequestType(RequestType.TOP_RATED)
    }

    private fun getNowPlaying() {
        moviesListViewModule.updateRequestType(RequestType.NOW_PLAYING)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}