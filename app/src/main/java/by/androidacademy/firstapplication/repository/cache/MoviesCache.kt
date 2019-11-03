package by.androidacademy.firstapplication.repository.cache

import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.data.Video

interface MoviesCache {

    fun isCached(): Boolean

    fun getMovies(): List<Movie>

    fun getMovieTrailerUrl(movieId: MovieId): String?

    fun insertMovies(movies: List<Movie>)

    fun insertVideo(video: Video)

    fun clearCache()
}