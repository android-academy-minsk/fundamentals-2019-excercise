package by.androidacademy.firstapplication.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import by.androidacademy.firstapplication.data.Movie

class DetailsFragmentAdapter(
    fragmentManager: FragmentManager,
    private val arrayList: List<Movie>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return DetailsFragment.newInstance(arrayList[position])
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}