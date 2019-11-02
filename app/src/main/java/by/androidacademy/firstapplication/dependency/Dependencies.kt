package by.androidacademy.firstapplication.dependency

import androidx.room.Room
import by.androidacademy.firstapplication.App
import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.db.AppDatabase
import by.androidacademy.firstapplication.repository.MoviesRepository
import by.androidacademy.firstapplication.repository.TmdbServiceMapper
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Dependencies {

    private val db by lazy {
        createRoomDatabase()
    }

    val moviesRepository by lazy {
        createMoviesRepository()
    }

    private fun createMoviesRepository(): MoviesRepository {
        return MoviesRepository(
            createTmdbServiceApi(),
            createTmdbServiceMapper(),
            db.movieDao(),
            db.videoDao()
        )
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
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun createRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(
            App.instance,
            AppDatabase::class.java,
            "movies.db"
        ).build()
    }
}