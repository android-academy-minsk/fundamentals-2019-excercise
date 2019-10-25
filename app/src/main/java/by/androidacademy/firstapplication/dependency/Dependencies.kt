package by.androidacademy.firstapplication.dependency

import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.repository.MoviesRepository
import by.androidacademy.firstapplication.repository.TmdbServiceMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Dependencies {

    val moviesRepository by lazy {
        createMoviesRepository()
    }

    private fun createMoviesRepository(): MoviesRepository {
        return MoviesRepository(createTmdbServiceApi(), createTmdbServiceMapper())
    }

    private fun createTmdbServiceApi(): TmdbServiceApi {
        return createRetrofit().create()
    }

    private fun createTmdbServiceMapper(): TmdbServiceMapper {
        return TmdbServiceMapper()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .build()
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}