package by.androidacademy.firstapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.androidacademy.firstapplication.R.layout
import by.androidacademy.firstapplication.data.Movie
import kotlinx.android.synthetic.main.fragment_gallery_details.vp_pager

private const val ARGS_MOVIE = "ARGS_MOVIE"
private const val ARGS_MOVIE_POSITION = "ARGS_MOVIE_POSITION"

class DetailsGalleryFragment : Fragment() {

    companion object {

        fun newInstance(
            movie: List<Movie>,
            position: Int
        ): DetailsGalleryFragment {
            val fragment = DetailsGalleryFragment()
            fragment.arguments = Bundle(2).apply {
                putParcelableArrayList(ARGS_MOVIE, ArrayList(movie))
                putInt(ARGS_MOVIE_POSITION, position)
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_gallery_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movies = arguments?.getParcelableArrayList<Movie>(ARGS_MOVIE)
            ?: throw IllegalArgumentException("Missing movie argument")
        val position = arguments?.getInt(ARGS_MOVIE_POSITION) ?: 0

        vp_pager.apply {
            adapter = DetailsFragmentAdapter(
                childFragmentManager,
                movies
            )
            currentItem = position
        }
    }

}
