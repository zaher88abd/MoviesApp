package dev.zaherabd.moviesapp.network.module

import com.google.gson.annotations.SerializedName

data class APIResponse(
    val page: Long,
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long,
)