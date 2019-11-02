package by.androidacademy.firstapplication.repository

import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.data.Video
import by.androidacademy.firstapplication.db.MovieDao
import by.androidacademy.firstapplication.db.VideoDao

class MoviesRepository(
    private val tmdbServiceApi: TmdbServiceApi,
    private val tmdbServiceMapper: TmdbServiceMapper,
    private val movieDao: MovieDao,
    private val videoDao: VideoDao
) {

    fun getCachedPopularMovies(): List<Movie> {
        return movieDao.getAll()
    }

    suspend fun getPopularMovies(): List<Movie> {
        val popularMoviesDto = tmdbServiceApi.getPopularMovies()
        val movies = tmdbServiceMapper.map(popularMoviesDto)

        movieDao.deleteAll()
        movieDao.insertAll(movies)

        return movies
    }

    fun getCachedMovieTrailerUrl(movie: Movie): String? {
        val video = videoDao.getVideoByMovieId(movie.id)
        return video?.url
    }

    suspend fun getMovieTrailerUrl(movie: Movie): String {
        val movieVideosDto = tmdbServiceApi.getMovieVideos(movie.id)
        val url = tmdbServiceMapper.mapTrailerUrl(movieVideosDto.results.first())

        videoDao.insert(Video(movie.id, url))

        return url
    }
}