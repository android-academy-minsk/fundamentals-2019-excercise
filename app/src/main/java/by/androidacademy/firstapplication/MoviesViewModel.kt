package by.androidacademy.firstapplication

import androidx.lifecycle.*
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val moviesMutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    val movies: LiveData<List<Movie>> = moviesMutableLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = moviesRepository.getPopularMovies()

            withContext(Dispatchers.Main) {
                moviesMutableLiveData.value = movies
            }
        }
    }
}

class MoviesViewModelFactory(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == MoviesViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            MoviesViewModel(moviesRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
