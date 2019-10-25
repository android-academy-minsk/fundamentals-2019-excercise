package by.androidacademy.firstapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.androidacademy.firstapplication.adapters.MoviesAdapter
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import by.androidacademy.firstapplication.threads.CoroutineActivity

class MoviesActivity : AppCompatActivity() {

    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initMoviesList()

        loadMovies()
    }

    private fun initMoviesList() {
        adapter = MoviesAdapter(this, emptyList()) { movies, position ->
            showDetailsFragment(movies, position)
        }

        val list = findViewById<RecyclerView>(R.id.moviesList)
        list.adapter = adapter

        val decoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(this, R.color.grey)!!)
        list.addItemDecoration(decoration)
    }

    private fun loadMovies() {
        val viewModel = ViewModelProviders.of(
            this,
            MoviesViewModelFactory(Dependencies.moviesRepository)
        ).get(MoviesViewModel::class.java)
        viewModel.movies.observe(this, Observer { movies ->
            adapter.movies = movies
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open_coroutine -> {
                // Open Async Task Activity
                startActivity(Intent(this, CoroutineActivity::class.java))
                true
            }

            else ->
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
        }
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