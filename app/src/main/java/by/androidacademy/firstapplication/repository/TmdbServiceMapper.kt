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
            posterRes = 0,
            backdropRes = 0,
            overview = movieDto.overview,
            releaseDate = movieDto.releaseDate,
            trailerUrl = ""
        )
    }
}