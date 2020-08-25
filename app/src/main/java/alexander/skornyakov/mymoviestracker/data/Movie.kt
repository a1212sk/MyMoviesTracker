package alexander.skornyakov.mymoviestracker.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("genre_ids")
    val genreIds: Array<Int>,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val overview: String,
    val release_date: Date,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String
) {

    private lateinit var genres: List<Genre>
    fun getGenres() = genres
    fun setGenres(genres: List<Genre>){
        this.genres = genres
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (voteAverage != other.voteAverage) return false
        if (overview != other.overview) return false
        if (release_date != other.release_date) return false
        if (backdropPath != other.backdropPath) return false
        if (originalTitle != other.originalTitle) return false
        if (posterPath != other.posterPath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + release_date.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + posterPath.hashCode()
        return result
    }
}

