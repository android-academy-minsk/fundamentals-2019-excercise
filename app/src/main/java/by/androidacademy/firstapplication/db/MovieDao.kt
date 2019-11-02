package by.androidacademy.firstapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.androidacademy.firstapplication.data.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    fun getAll(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM Movie")
    fun deleteAll()
}