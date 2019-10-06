package by.androidacademy.firstapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.androidacademy.firstapplication.adapters.MoviesAdapter
import by.androidacademy.firstapplication.data.DataStorage
import by.androidacademy.firstapplication.data.Movie

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val movies = DataStorage.getMoviesList()
        val adapter = MoviesAdapter(this, movies) { position ->
            showDetailsFragment(movies, position)
        }

        val list = findViewById<RecyclerView>(R.id.moviesList)
        list.adapter = adapter

        val decoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(this, R.color.grey)!!)
        list.addItemDecoration(decoration)
    }

    private fun showDetailsFragment(
        movies: List<Movie>,
        position: Int
    ) {
        val detailsFragment = DetailsGalleryFragment.newInstance(movies, position)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.container, detailsFragment)
            .commit()
    }
}