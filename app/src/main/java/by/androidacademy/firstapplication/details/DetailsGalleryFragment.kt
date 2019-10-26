package by.androidacademy.firstapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie

class DetailsGalleryFragment : Fragment() {

    companion object {

        private const val ARGS_MOVIE = "ARGS_MOVIE"
        private const val ARGS_MOVIE_POSITION = "ARGS_MOVIE_POSITION"

        fun newInstance(
            movie: List<Movie>,
            position: Int
        ): DetailsGalleryFragment {
            val fragment = DetailsGalleryFragment()
            val bundle = Bundle()
            bundle.run {
                putParcelableArrayList(ARGS_MOVIE, ArrayList(movie))
                putInt(ARGS_MOVIE_POSITION, position)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery_details, container, false)
        val movies = arguments?.getParcelableArrayList<Movie>(ARGS_MOVIE)
            ?: throw IllegalArgumentException("Missing movie argument")
        val position = arguments?.getInt(ARGS_MOVIE_POSITION) ?: 0

        view.findViewById<ViewPager>(R.id.vp_pager).run {
            adapter = DetailsFragmentAdapter(
                childFragmentManager,
                movies
            )
            currentItem = position
        }

        return view
    }


}
