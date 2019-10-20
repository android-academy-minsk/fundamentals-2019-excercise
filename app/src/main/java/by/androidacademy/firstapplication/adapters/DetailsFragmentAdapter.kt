package by.androidacademy.firstapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import by.androidacademy.firstapplication.DetailsFragment
import by.androidacademy.firstapplication.data.Movie

class DetailsFragmentAdapter(
    fragmentManager: FragmentManager,
    private val arrayList: List<Movie>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        val movie = when {
            arrayList.isNotEmpty() && position <= count - 1 -> arrayList[position]
            else -> null
        }
        return movie?.run { DetailsFragment.newInstance(this) }
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}