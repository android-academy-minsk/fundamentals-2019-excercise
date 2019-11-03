package by.androidacademy.firstapplication.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import coil.api.load
import kotlinx.android.synthetic.main.fragment_details.details_btn_trailer
import kotlinx.android.synthetic.main.fragment_details.details_iv_back
import kotlinx.android.synthetic.main.fragment_details.details_iv_image
import kotlinx.android.synthetic.main.fragment_details.details_tv_overview_text
import kotlinx.android.synthetic.main.fragment_details.details_tv_released_date
import kotlinx.android.synthetic.main.fragment_details.details_tv_title

private const val ARGS_MOVIE = "ARGS_MOVIE"

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = arguments?.getParcelable(ARGS_MOVIE)
            ?: throw IllegalArgumentException("Missing movie argument")

        viewModel = ViewModelProviders.of(
            this,
            DetailsViewModelFactory(
                Dependencies.moviesRepository,
                requireContext(),
                movie
            )
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
            viewLifecycleOwner,
            Observer { trailerUrl -> openMovieTrailer(trailerUrl) }
        )
        viewModel.error.observe(
            viewLifecycleOwner,
            Observer { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )

        details_iv_back.load(movie.backdropUrl)
        details_iv_image.load(movie.posterUrl)
        details_tv_title.text = movie.title
        details_tv_released_date.text = movie.releaseDate
        details_tv_overview_text.text = movie.overview
        details_btn_trailer.setOnClickListener { viewModel.onTrailerButtonClicked() }
    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {

        fun newInstance(movie: Movie): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = Bundle(1).apply {
                putParcelable(ARGS_MOVIE, movie)
            }

            return fragment
        }
    }
}
