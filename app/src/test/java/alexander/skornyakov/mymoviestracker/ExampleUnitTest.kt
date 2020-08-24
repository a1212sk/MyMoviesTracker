package alexander.skornyakov.mymoviestracker

import alexander.skornyakov.mymoviestracker.repository.TmdbRepository
import alexander.skornyakov.mymoviestracker.repository.api.TmdbApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getMovies_returnsListOfMovies(){
        runBlocking {
            val list = TmdbRepository().getPopularMovies()
            println(list)
        }
    }
}