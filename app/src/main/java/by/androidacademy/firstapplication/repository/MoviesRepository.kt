package by.androidacademy.firstapplication.repository

import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.data.Video
import by.androidacademy.firstapplication.repository.cache.MoviesCache

class MoviesRepository(
    private val tmdbServiceApi: TmdbServiceApi,
    private val tmdbServiceMapper: TmdbServiceMapper,
    private val cache: MoviesCache
) {

    suspend fun getPopularMovies(): List<Movie> {
        if (cache.isCached()) {
            return cache.getMovies()
        }

        val popularMoviesDto = tmdbServiceApi.getPopularMovies()
        val movies = tmdbServiceMapper.map(popularMoviesDto)

        cache.insertMovies(movies)

        return movies
    }

    fun getCachedMovieTrailerUrl(movie: Movie): String? {
        return cache.getMovieTrailerUrl(movie.id)
    }

    suspend fun getMovieTrailerUrl(movie: Movie): String {
        val movieVideosDto = tmdbServiceApi.getMovieVideos(movie.id)
        val url = tmdbServiceMapper.mapTrailerUrl(movieVideosDto.results.first())

        cache.insertVideo(Video(movie.id, url))

        return url
    }

    fun deleteCachedData() {
        cache.clearCache()
    }
}