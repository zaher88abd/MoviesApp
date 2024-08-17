package dev.zaherabd.moviesapp.network

import dev.zaherabd.moviesapp.network.module.APIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesCallService {
    @GET("3/discover/upcoming")
    fun getUpcoming(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("moviewith_release_type") moviewithReleaseType: String = "2|3",
        @Query("release_date.gte") releaseDateGte: String = "{min_date}",
        @Query("release_date.lte") releaseDateLte: String = "{max_date}",
    ): Call<APIResponse>

    @GET("3/discover/top_rated")
    fun getTopRated(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "ivote_average.desc",
        @Query("without_genres") withoutGenres: String = "99,10755",
        @Query("vote_count.gte") releaseDateGte: Int = 200,
    ): Call<APIResponse>

    @GET("3/discover/popular")
    fun getPopular(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): Call<APIResponse>

    @GET("3/discover/now_playing")
    fun getNowPlaying(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("moviewith_release_type") moviewithReleaseType: String = "2|3",
        @Query("release_date.gte") releaseDateGte: String = "{min_date}",
        @Query("release_date.lte") releaseDateLte: String = "{max_date}",
    ): Call<APIResponse>
}