package alexander.skornyakov.mymoviestracker.viewmodels

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val repo = TmdbRepository.getInstance()

    val movies = MutableLiveData<List<Movie>>()

    private var job: Job = Job()
    fun loadMovies(query: String = "") {
        job.cancel()
        job = Job()
        CoroutineScope(Dispatchers.Main + job).launch {
            repo?.let {
                if (query.isEmpty()) {
                    movies.value = repo.getPopularMovies()?.results
                } else {
                    movies.value = repo.searchMovies(query)?.results
                }
            }
        }
    }

}