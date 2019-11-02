package by.androidacademy.firstapplication.repository

import by.androidacademy.firstapplication.api.dto.MovieDto
import by.androidacademy.firstapplication.api.dto.MovieVideoDto
import by.androidacademy.firstapplication.api.dto.PopularMoviesDto
import by.androidacademy.firstapplication.data.Movie

class TmdbServiceMapper {

    fun map(popularMoviesDto: PopularMoviesDto): List<Movie> {
        return popularMoviesDto.results.mapIndexed { index, movieDto -> map(movieDto, index) }
    }

    private fun map(movieDto: MovieDto, popularity: Int): Movie {
        return Movie(
            id = movieDto.id,
            title = movieDto.title,
            posterUrl = POSTER_BASE_URL + movieDto.posterPath,
            backdropUrl = BACKDROP_BASE_URL + movieDto.backdropPath,
            overview = movieDto.overview,
            releaseDate = movieDto.releaseDate,
            popularity = popularity
        )
    }

    fun mapTrailerUrl(movieVideoDto: MovieVideoDto): String {
        return YOUTUBE_BASE_URL + movieVideoDto.key
    }

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
        private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    }
}