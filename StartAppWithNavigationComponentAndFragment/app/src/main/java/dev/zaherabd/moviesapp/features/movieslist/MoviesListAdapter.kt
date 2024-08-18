package dev.zaherabd.moviesapp.features.movieslist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.zaherabd.moviesapp.Constants
import dev.zaherabd.moviesapp.Constants.TAG
import dev.zaherabd.moviesapp.R
import dev.zaherabd.moviesapp.network.module.MovieResponse

class MoviesListAdapter() : RecyclerView.Adapter<MovieViewHolder>() {
    private var moviesList: List<MovieResponse>? = null
    var onItemClick: ((MovieResponse) -> Unit)? = null
    var onAddItemClicked: ((MovieResponse) -> Unit)? = null

    fun loadMoviesList(moviesList: List<MovieResponse>) {
        this.moviesList = moviesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        try {
            moviesList?.let { movies ->
                with(holder.itemView) {
                    findViewById<TextView>(R.id.tv_movie_name).text = movies[position].title
                    findViewById<TextView>(R.id.tv_movie_rate).text =
                        movies[position].voteAverage.toString()
                    findViewById<TextView>(R.id.tv_movie_release_date).text =
                        movies[position].releaseDate
                    val posterIV = findViewById<ImageView>(R.id.iv_movie)
                    val addMovieBtn = findViewById<ImageView>(R.id.iv_add_movie)
                    Glide.with(context)
                        .load(Constants.IMAGES_BASE_URL + movies[position].posterPath)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(posterIV)
                    setOnClickListener {
                        onItemClick?.invoke(movies[position])
                    }
                    addMovieBtn.setOnClickListener {
                        onAddItemClicked?.invoke(movies[position])
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onBindViewHolder: ", e)
        }
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
