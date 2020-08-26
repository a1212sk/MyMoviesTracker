package alexander.skornyakov.mymoviestracker.repository.api

import alexander.skornyakov.mymoviestracker.data.Genre
import alexander.skornyakov.mymoviestracker.data.Genres
import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.Movies
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<Movies>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id:Int): Response<Movie>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<Genres>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query")query: String): Response<Movies>
}