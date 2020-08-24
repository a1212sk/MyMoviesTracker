package alexander.skornyakov.mymoviestracker.data

import java.util.*

data class Movie(
    val id: String,
    val title: String,
    val genres: Array<Int>,
    val vote: Float,
    val overview: String,
    val releaseDate: Date,
    val poster: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (!genres.contentEquals(other.genres)) return false
        if (vote != other.vote) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (poster != other.poster) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + genres.contentHashCode()
        result = 31 * result + vote.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + poster.hashCode()
        return result
    }
}