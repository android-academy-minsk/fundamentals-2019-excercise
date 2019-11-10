package by.androidacademy.firstapplication.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import by.androidacademy.firstapplication.details.DetailsGalleryFragment
import by.androidacademy.firstapplication.services.BGServiceActivity
import by.androidacademy.firstapplication.threads.CoroutineActivity
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    private lateinit var internalAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)

        initViewModel()

        initMoviesList()
        initProgressBar()
        initErrorHandler()

        loadMovies()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            MoviesViewModelFactory(
                Dependencies.moviesRepository,
                this
            )
        ).get(MoviesViewModel::class.java)
    }

    private fun initMoviesList() {
        internalAdapter = MoviesAdapter(
            this,
            emptyList()
        ) { movies, position -> showDetailsFragment(movies, position) }

        val decoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        ContextCompat.getDrawable(this, R.color.grey)
            ?.let { image -> decoration.setDrawable(image) }

        moviesList.apply {
            adapter = internalAdapter
            addItemDecoration(decoration)
        }

        viewModel.movies.observe(this, Observer { movies ->
            internalAdapter.movies = movies
            internalAdapter.notifyDataSetChanged()
        })
    }

    private fun initProgressBar() {
        viewModel.isProgressBarVisible.observe(this, Observer { isVisible ->
            moviesProgressBar.isVisible = isVisible
        })
    }

    private fun initErrorHandler() {
        viewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    private fun loadMovies() {
        viewModel.loadPopularMovies()
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

            R.id.action_delete_all_movies -> {
                viewModel.deleteAllDataFromDatabase()

                true
            }

            R.id.action_open_background_service_activity -> {
                startActivity(Intent(this, BGServiceActivity::class.java))
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
        val detailsFragment =
            DetailsGalleryFragment.newInstance(
                movies,
                position
            )
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.container, detailsFragment)
            .commit()
    }
}