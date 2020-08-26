package alexander.skornyakov.mymoviestracker.data.firebase

data class FbMovie(
    var id : Long = 0,
    var vote: Int = -1,
    var note: String = "",
    var watched: Boolean = false
)