package by.androidacademy.firstapplication.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val title: String,
    @DrawableRes val posterRes: Int,
    @DrawableRes val backdropRes: Int,
    val overview: String,
    val releaseDate: String,
    val trailerUrl: String
) : Parcelable