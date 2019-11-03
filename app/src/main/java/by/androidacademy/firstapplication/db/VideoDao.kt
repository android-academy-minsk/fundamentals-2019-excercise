package by.androidacademy.firstapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.androidacademy.firstapplication.data.Video

@Dao
interface VideoDao {

    @Query("SELECT * FROM Video WHERE movieId = :movieId")
    fun getVideoByMovieId(movieId: Int): Video?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: Video)

    @Query("DELETE FROM Video")
    fun deleteAll()
}