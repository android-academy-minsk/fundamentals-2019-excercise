package by.androidacademy.firstapplication.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesDto(
    val results: List<MovieDto>
)
