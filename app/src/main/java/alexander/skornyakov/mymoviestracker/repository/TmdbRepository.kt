package alexander.skornyakov.mymoviestracker.repository

import alexander.skornyakov.mymoviestracker.data.Genre
import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import alexander.skornyakov.mymoviestracker.repository.api.TmdbApiFactory

class TmdbRepository private constructor() {

    companion object {
        private var repository: TmdbRepository? = null
        fun getInstance(): TmdbRepository? {
            if (repository == null) {
                repository = TmdbRepository()
            }
            return repository
        }
    }

    suspend fun getPopularMovies(): Movies? {
        val result = TmdbApiFactory.tmdbApi.getPopularMovies()
        if (result.isSuccessful) {
            result.body()?.let { movies ->
                getGenres()?.let {
                    for (movie in movies.results) {
                        val genreList = movie.genreIds
                            .map { index ->
                                it.single { index == it.id }
                            }
                        movie.setGenres(genreList)
                    }
                    return movies
                }
            }
        }
        println(result.message() + " " + result.errorBody())
        return null
    }

    suspend fun searchMovies(query: String): Movies? {
        val result = TmdbApiFactory.tmdbApi.searchMovies(query)
        if (result.isSuccessful) {
            result.body()?.let { movies ->
                getGenres()?.let {
                    for (movie in movies.results) {
                        val genreList = movie.genreIds
                            .map { index ->
                                it.single { index == it.id }
                            }
                        movie.setGenres(genreList)
                    }
                    return movies
                }
            }
        }
        println(result.message() + " " + result.errorBody())
        return null
    }

    suspend fun getMovie(id: Int): Movie? {
        val result = TmdbApiFactory.tmdbApi.getMovieById(id)
        if (result.isSuccessful) {
            result.body()?.let { movie ->
                getGenres()?.let {
                    val genreList = movie.genreIds.map { index ->
                        it.single { it.id == index }
                    }
                    movie.setGenres(genreList)
                }
                return movie
            }
        }
        println(result.message())
        return null
    }

    private var genres: List<Genre>? = null
    suspend fun getGenres(): List<Genre>? {
        if (genres == null) {
            val result = TmdbApiFactory.tmdbApi.getGenres()
            if (result.isSuccessful) {
                result.body()?.let {
                    genres = it.genres
                }
            }
            println(result.message())
        }
        return genres
    }

}

