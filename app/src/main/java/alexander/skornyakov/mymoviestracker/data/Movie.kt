package alexander.skornyakov.mymoviestracker.data

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val genre_ids: Array<Int>,
    val vote_average: Double,
    val overview: String,
    val release_date: Date,
    val poster_path: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (!genre_ids.contentEquals(other.genre_ids)) return false
        if (vote_average != other.vote_average) return false
        if (overview != other.overview) return false
        if (release_date != other.release_date) return false
        if (poster_path != other.poster_path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + genre_ids.contentHashCode()
        result = 31 * result + vote_average.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + release_date.hashCode()
        result = 31 * result + poster_path.hashCode()
        return result
    }
}
