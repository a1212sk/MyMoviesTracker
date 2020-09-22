package alexander.skornyakov.mymoviestracker.viewmodels

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.firebase.FbMovie
import alexander.skornyakov.mymoviestracker.repository.FbRepository
import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchingViewModel @ViewModelInject constructor(
    val tmdbRepository: TmdbRepository,
    val fbRepository: FbRepository
) : ViewModel() {

    val watchingMovies = fbRepository.getMovies(false)



}