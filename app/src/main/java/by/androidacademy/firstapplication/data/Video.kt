package by.androidacademy.firstapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    @PrimaryKey val movieId: Int,
    val url: String
)