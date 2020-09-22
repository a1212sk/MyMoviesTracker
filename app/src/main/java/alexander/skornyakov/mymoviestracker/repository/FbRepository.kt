package alexander.skornyakov.mymoviestracker.repository

import alexander.skornyakov.mymoviestracker.data.Movie
import alexander.skornyakov.mymoviestracker.data.firebase.FbMovie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FbRepository
@Inject constructor(
    val db: FirebaseFirestore,
    val auth: FirebaseAuth,
    val tmdbRepository: TmdbRepository
) {

    fun getUser(): Query {
        return db.collection("users")
            .whereEqualTo("userID", auth.uid)
    }


    fun getMovies(watched: Boolean): LiveData<List<Movie>> {
        val moviesLiveData = MutableLiveData<List<Movie>>()
            CoroutineScope(Dispatchers.IO).launch {
                getUser().get().await().documents.first().reference.collection("movies")
                    .whereEqualTo("watched", watched).addSnapshotListener { value, error ->
                        var movies = mutableListOf<Movie>()
                        var fbMovies = mutableListOf<FbMovie>()
                        value?.documents?.let {
                            for (doc in it) {
                                val id = doc.get("id") as Long
                                val vote = (doc.get("vote") as Long).toInt()
                                val note = doc.get("note") as String
                                val movie = FbMovie(id, vote, note, watched)
                                fbMovies.add(movie)
                            }
                            CoroutineScope(Dispatchers.IO).launch {
                                for (m in fbMovies) {
                                    val movie = tmdbRepository.getMovie(m.id.toInt())
                                    movie?.let {
                                        movies.add(movie)
                                    }
                                }
                                moviesLiveData.postValue(movies)
                            }
                        }

                    }
            }

        return moviesLiveData

    }

    suspend fun addMovie(movie: FbMovie) {
        val users = db.collection("users")
            .whereEqualTo("userID", auth.uid)
            .get().await()
            .documents
        users?.let {
            if (it.count() > 0) {
                var user = it[0]
                user.reference.collection("movies")
                    .add(movie).await()
            }
        }
    }

    suspend fun existsWithId(id: Long, watched: Boolean): Boolean {
        val users = db.collection("users")
            .whereEqualTo("userID", auth.uid)
            .get().await()
            .documents
        users?.let {
            if (it.count() > 0) {
                var user = it[0]
                val exists = user.reference.collection("movies")
                    .whereEqualTo("id", id)
                    .whereEqualTo("watched", watched)
                    .get().await()
                    .documents.size > 0
                return exists
            }
        }
        return false
    }

    suspend fun existsWithId(id: Long): Boolean {
        val users = db.collection("users")
            .whereEqualTo("userID", auth.uid)
            .get().await()
            .documents
        users?.let {
            if (it.count() > 0) {
                var user = it[0]
                val exists = user.reference.collection("movies")
                    .whereEqualTo("id", id)
                    .get().await()
                    .documents.size > 0
                return exists
            }
        }
        return false
    }

    fun updateMovie(fbMovie: FbMovie): Task<Unit?> {
        return db.collection("users")
            .whereEqualTo("userID", auth.uid)
            .get().continueWith {
                val users = it.result?.documents
                users?.let {
                    if (it.count() > 0) {
                        var user = it[0]
                        user.reference.collection("movies")
                            .whereEqualTo("id", fbMovie.id)
                            .get().continueWith {
                                it.result?.documents?.let {
                                    if (it.size > 0) {
                                        var doc = it[0]
                                        return@continueWith doc.reference.update(
                                            "watched",
                                            fbMovie.watched
                                        )
                                    }
                                }
                            }


                    }
                }
            }


    }

}