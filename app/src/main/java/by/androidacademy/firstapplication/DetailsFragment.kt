package by.androidacademy.firstapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import coil.api.load

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(ARGS_MOVIE)!!
        viewModel = ViewModelProviders.of(
            this,
            DetailsViewModelFactory(Dependencies.moviesRepository, movie)
        ).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openTrailerUrl.observe(
            this,
            Observer { trailerUrl -> openMovieTrailer(trailerUrl) }
        )

        view.findViewById<ImageView>(R.id.details_iv_back).load(movie.backdropUrl)
        view.findViewById<ImageView>(R.id.details_iv_image).load(movie.posterUrl)
        view.findViewById<TextView>(R.id.details_tv_title).text = movie.title
        view.findViewById<TextView>(R.id.details_tv_released_date).text = movie.releaseDate
        view.findViewById<TextView>(R.id.details_tv_overview_text).text = movie.overview

        val movieButton: Button = view.findViewById(R.id.details_btn_trailer)
        movieButton.setOnClickListener { viewModel.onTrailerButtonClicked() }
    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {

        private const val ARGS_MOVIE = "ARGS_MOVIE"

        fun newInstance(movie: Movie): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_MOVIE, movie)
            fragment.arguments = bundle
            return fragment
        }
    }
}
