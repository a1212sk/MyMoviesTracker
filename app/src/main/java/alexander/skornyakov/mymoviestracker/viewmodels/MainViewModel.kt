package alexander.skornyakov.mymoviestracker.viewmodels

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import alexander.skornyakov.mymoviestracker.data.firebase.FbMovie
import alexander.skornyakov.mymoviestracker.repository.FbRepository
import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel
@ViewModelInject constructor(val repo: TmdbRepository, val fbRepository: FbRepository) : ViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    private var job: Job = Job()
    fun loadMovies(query: String = "") : Job{
        job.cancel()
        job = Job()
        return CoroutineScope(Dispatchers.Main + job).launch {
            repo.let {
                if (query.isEmpty()) {
                    movies.value = repo.getPopularMovies()?.results
                } else {
                    movies.value = repo.searchMovies(query)?.results
                }
            }
        }
    }

    fun addToWatching(movie: Movie){
        var fbMovie = FbMovie()
        fbMovie.id = movie.id.toLong()
        fbMovie.watched = false
        CoroutineScope(Dispatchers.IO).launch {
            fbRepository.addMovie(fbMovie)
        }
    }

    fun addToWatched(movie: Movie){
        var fbMovie = FbMovie()
        fbMovie.id = movie.id.toLong()
        fbMovie.watched = true
        CoroutineScope(Dispatchers.IO).launch {
            fbRepository.addMovie(fbMovie)
        }
    }

    fun alreadyExists(id: Long, watched: Boolean):LiveData<Boolean> = liveData{
            val exists = fbRepository.existsWithId(id, watched)
            emit(exists)
    }

    fun alreadyExists(id: Long):LiveData<Boolean> = liveData{
        val exists = fbRepository.existsWithId(id)
        emit(exists)
    }

    fun toWatched(movie: Movie) {
        var fbMovie = FbMovie()
        fbMovie.id = movie.id.toLong()
        fbMovie.watched = true
        CoroutineScope(Dispatchers.IO).launch {
            fbRepository.updateMovie(fbMovie)

        }
    }



}