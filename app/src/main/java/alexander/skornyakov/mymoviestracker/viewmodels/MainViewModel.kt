package alexander.skornyakov.mymoviestracker.viewmodels

import alexander.skornyakov.mymoviestracker.data.Movies
import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class MainViewModel : ViewModel(){

    private val repo = TmdbRepository.getInstance()

    fun getPopularMovies() = liveData {
        repo?.let {
            val data = repo.getPopularMovies()
            emit(data)
        }
    }

    val genres = liveData {
        repo?.getGenres()?.let {
            emit(it)
        }
    }

}