package by.androidacademy.firstapplication.api

import by.androidacademy.firstapplication.api.dto.MovieVideosDto
import by.androidacademy.firstapplication.api.dto.PopularMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbServiceApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Secret.SERVER_API_KEY
    ): PopularMoviesDto


    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = Secret.SERVER_API_KEY
    ): MovieVideosDto
}