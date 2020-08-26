package alexander.skornyakov.mymoviestracker.viewmodels

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.repository.FbRepository
import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class WatchingViewModel @ViewModelInject constructor(
    val tmdbRepository: TmdbRepository,
    val fbRepository: FbRepository
) : ViewModel() {

    val watchedMovies = liveData<List<Movie>> {
        val listOfWatchedMovies = fbRepository.getAllMyMovies(false)
        val result = mutableListOf<Movie>()
        for(m in listOfWatchedMovies){
            val movie = tmdbRepository.getMovie(m.id.toInt())
            movie?.let {
                result.add(movie)
            }
        }
        emit(result)
    }

}