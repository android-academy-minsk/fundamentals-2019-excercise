package by.androidacademy.firstapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.data.Video

@Database(entities = [Movie::class, Video::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun videoDao(): VideoDao
}