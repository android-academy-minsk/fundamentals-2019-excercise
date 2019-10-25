package by.androidacademy.firstapplication.repository

import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.data.Movie

class MoviesRepository(
    private val tmdbServiceApi: TmdbServiceApi,
    private val tmdbServiceMapper: TmdbServiceMapper
) {

    suspend fun getPopularMovies(): List<Movie> {
        val popularMoviesDto = tmdbServiceApi.getPopularMovies()
        return tmdbServiceMapper.map(popularMoviesDto)
    }
}