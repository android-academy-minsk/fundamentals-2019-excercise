package by.androidacademy.firstapplication.api.dto

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String
)
