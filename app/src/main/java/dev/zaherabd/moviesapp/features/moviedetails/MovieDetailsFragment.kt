package dev.zaherabd.moviesapp.features.moviedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.zaherabd.moviesapp.Constants
import dev.zaherabd.moviesapp.Constants.TAG
import dev.zaherabd.moviesapp.databinding.FragmentMoiveDetailsBinding
import dev.zaherabd.moviesapp.network.module.MovieResponse

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMoiveDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoiveDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movieObj
        binding.tvMovieTitle.text = movie.title
        binding.tvMovieReleaseDate.text = movie.releaseDate
        this.context?.let {
            Glide.with(it)
                .load(Constants.IMAGES_BASE_URL + movie.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.ivMoviePoster)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}