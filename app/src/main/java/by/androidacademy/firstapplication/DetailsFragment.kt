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
import by.androidacademy.firstapplication.data.Movie

class DetailsFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(ARGS_MOVIE)?.run {

            view.findViewById<ImageView>(R.id.details_iv_back).setImageResource(backdropRes)
            view.findViewById<ImageView>(R.id.details_iv_image).setImageResource(posterRes)
            view.findViewById<TextView>(R.id.details_tv_title).text = title
            view.findViewById<TextView>(R.id.details_tv_released_date).text = releaseDate
            view.findViewById<TextView>(R.id.details_tv_overview_text).text = overview

            val movieButton: Button = view.findViewById(R.id.details_btn_trailer)
            movieButton.setOnClickListener {
                openMovieTrailer(trailerUrl)
            }
        }

    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


}
