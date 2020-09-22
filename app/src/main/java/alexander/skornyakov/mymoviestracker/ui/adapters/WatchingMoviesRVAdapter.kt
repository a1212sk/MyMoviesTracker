package alexander.skornyakov.mymoviestracker.ui.adapters

import alexander.skornyakov.mymoviestracker.Constants
import alexander.skornyakov.mymoviestracker.GlideApp
import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.data.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_watching_movies_item.view.*

import java.text.SimpleDateFormat
import java.util.*

class WatchingMoviesRVAdapter : RecyclerView.Adapter<WatchingMoviesRVAdapter.MovieViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.rv_watching_movies_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var addWatchedMovieListener: ((Movie)->Unit)? = null
    fun setWatchedListener(callback: (Movie)->Unit){
        addWatchedMovieListener = callback
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.itemView.apply {
            tvTitle.text = movie.title
            if(movie.originalTitle == movie.title){
                tvOriginalTitle.visibility = View.GONE
            }else {
                tvOriginalTitle.visibility = View.VISIBLE
                tvOriginalTitle.text = movie.originalTitle
            }
            if(movie.release_date.isNotEmpty()) {
                val date = SimpleDateFormat("yyyy-MM-dd").parse(movie.release_date)
                Calendar.getInstance().apply {
                    time = date
                    tvYear.text = get(Calendar.YEAR).toString()
                }
            }
            tvOverview.text = movie.overview
            //tvAvgVote.text = "Average vote: ${movie.voteAverage}"
            GlideApp.with(this)
                .load(Constants.TMDB_IMAGE_BASE + movie.posterPath)
                .into(imgPoster)
            val genres = movie.getGenres().map {
                it.name
            }.joinToString(", ")
            tvGenre.text = genres
            btnWatched.setOnClickListener { addWatchedMovieListener?.run { this(movie) } }
        }
    }

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer<Movie>(this, diffCallback)

    fun submitList(list: List<Movie>) = differ.submitList(list)

}