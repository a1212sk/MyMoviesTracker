package alexander.skornyakov.mymoviestracker.repository

import alexander.skornyakov.mymoviestracker.data.firebase.FbMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FbRepository
@Inject constructor(val db: FirebaseFirestore, val auth: FirebaseAuth) {

    suspend fun getAllMyMovies(watched: Boolean): List<FbMovie> {
        val movies = mutableListOf<FbMovie>()
        val users = db.collection("users")
            .whereEqualTo("userID", auth.uid)
            .get().await()
            .documents
        users?.let {
            if (it.count() > 0) {
                var user = it[0]
                val moviesDocs = user.reference.collection("movies")
                    .whereEqualTo("watched", watched)
                    .get().await().documents
                for (doc in moviesDocs) {
                    val id = doc.get("id") as Long
                    val vote = (doc.get("vote") as Long).toInt()
                    val note = doc.get("note") as String
                    val movie = FbMovie(id, vote, note, watched)
                    movies.add(movie)
                }
            }
        }
        return movies
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

    suspend fun existsWithId(id: Long): Boolean {
        val users = db.collection("users")
            .whereEqualTo("userID", auth.uid)
            .get().await()
            .documents
        users?.let {
            if(it.count() > 0){
                var user = it[0]
                val exists = user.reference.collection("movies")
                    .whereEqualTo("id",id)
                    .get().await()
                    .documents.size>0
                return exists
            }
        }
        return false
    }

}