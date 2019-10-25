package by.androidacademy.firstapplication.repository

import by.androidacademy.firstapplication.api.dto.MovieDto
import by.androidacademy.firstapplication.api.dto.PopularMoviesDto
import by.androidacademy.firstapplication.data.Movie

class TmdbServiceMapper {

    fun map(popularMoviesDto: PopularMoviesDto): List<Movie> {
        return popularMoviesDto.results.map { movieDto -> map(movieDto) }
    }

    private fun map(movieDto: MovieDto): Movie {
        // TODO:
        return Movie(
            title = movieDto.title,
            posterUrl = POSTER_BASE_URL + movieDto.posterPath,
            backdropUrl = BACKDROP_BASE_URL + movieDto.backdropPath,
            overview = movieDto.overview,
            releaseDate = movieDto.releaseDate,
            trailerUrl = ""
        )
    }

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
    }
}