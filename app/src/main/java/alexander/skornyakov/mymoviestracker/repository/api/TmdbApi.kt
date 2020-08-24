package alexander.skornyakov.mymoviestracker.repository.api

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<Movies>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id:Int): Response<Movie>
}