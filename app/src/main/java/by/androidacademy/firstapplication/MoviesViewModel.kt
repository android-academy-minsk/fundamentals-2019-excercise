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
    private val isProgressBarVisibleMutableLiveData = MutableLiveData<Boolean>()

    val movies: LiveData<List<Movie>> = moviesMutableLiveData
    val isProgressBarVisible: LiveData<Boolean> = isProgressBarVisibleMutableLiveData

    init {
        viewModelScope.launch {
            isProgressBarVisibleMutableLiveData.value = true

            val movies = withContext(Dispatchers.IO) { moviesRepository.getPopularMovies() }

            moviesMutableLiveData.value = movies
            isProgressBarVisibleMutableLiveData.value = false
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
