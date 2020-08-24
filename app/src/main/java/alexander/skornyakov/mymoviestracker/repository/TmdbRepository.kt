package alexander.skornyakov.mymoviestracker.repository

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import alexander.skornyakov.mymoviestracker.repository.api.TmdbApiFactory

class TmdbRepository {

    suspend fun getPopularMovies(): Movies?{
        val result = TmdbApiFactory.tmdbApi.getPopularMovies()
        if(result.isSuccessful){
            return result.body()
        }
        println(result.message() + " " + result.errorBody())
        return null
    }

    suspend fun getMovie(id: Int): Movie?{
        val result = TmdbApiFactory.tmdbApi.getMovieById(id)
        if(result.isSuccessful){
            return result.body()
        }
        println(result.message())
        return null
    }

}

