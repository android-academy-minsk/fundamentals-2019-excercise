package by.androidacademy.firstapplication.repository.cache

import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.data.Video
import by.androidacademy.firstapplication.db.MovieDao
import by.androidacademy.firstapplication.db.VideoDao

private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

typealias MovieId = Int

class MoviesCacheImpl constructor(
    private val movieDao: MovieDao,
    private val videoDao: VideoDao,
    private val preferenceHelper: SharedPreferencesApi
) : MoviesCache {

    override fun isCached(): Boolean {
        return movieDao.getAll().isNotEmpty() && !isExpired()
    }

    private fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()

        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferenceHelper.lastCacheTime
    }

    override fun getMovies(): List<Movie> {
        return movieDao.getAll()
    }

    override fun getMovieTrailerUrl(movieId: MovieId): String? {
        return videoDao.getVideoByMovieId(movieId)?.url
    }

    override fun insertMovies(movies: List<Movie>) {
        movieDao.deleteAll()
        movieDao.insertAll(movies)

        preferenceHelper.lastCacheTime = System.currentTimeMillis()
    }

    override fun insertVideo(video: Video) {
        videoDao.insert(video)
    }

    override fun clearCache() {
        movieDao.deleteAll()
        videoDao.deleteAll()
    }
}