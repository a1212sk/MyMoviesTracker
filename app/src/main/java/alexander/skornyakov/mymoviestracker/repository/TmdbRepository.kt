package alexander.skornyakov.mymoviestracker.repository

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import alexander.skornyakov.mymoviestracker.repository.api.TmdbApiFactory
import java.util.*

class TmdbRepository private constructor(){

    companion object{
        private var repository: TmdbRepository? = null
        fun getInstance():TmdbRepository?{
            if(repository==null){
                repository = TmdbRepository()
            }
            return repository
        }
    }

    suspend fun getPopularMovies(): Movies?{
        val result = TmdbApiFactory.tmdbApi.getPopularMovies()
        if(result.isSuccessful){
            result.body()?.let {
                return it
            }
        }
        println(result.message() + " " + result.errorBody())
        return null
    }

    suspend fun getMovie(id: Int): Movie?{
        val result = TmdbApiFactory.tmdbApi.getMovieById(id)
        if(result.isSuccessful){
            result.body()?.let{
                return it
            }
        }
        println(result.message())
        return null
    }

}

