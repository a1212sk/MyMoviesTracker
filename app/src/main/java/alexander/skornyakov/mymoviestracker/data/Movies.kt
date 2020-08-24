package alexander.skornyakov.mymoviestracker.data

data class Movies(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
)