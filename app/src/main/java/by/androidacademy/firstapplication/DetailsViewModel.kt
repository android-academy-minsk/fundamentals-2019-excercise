package by.androidacademy.firstapplication

import androidx.lifecycle.*
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.repository.MoviesRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val movie: Movie
) : ViewModel() {

    private val trailerUrlMutableLiveData = MutableLiveData<String>()

    val openTrailerUrl: LiveData<String> = trailerUrlMutableLiveData

    fun onTrailerButtonClicked() {

        viewModelScope.launch {
            trailerUrlMutableLiveData.value = moviesRepository.getMovieTrailerUrl(movie)
        }
    }
}

class DetailsViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val movie: Movie
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == DetailsViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            DetailsViewModel(moviesRepository, movie) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}