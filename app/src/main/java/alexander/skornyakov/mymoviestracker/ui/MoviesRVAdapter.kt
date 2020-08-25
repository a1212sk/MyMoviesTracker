package alexander.skornyakov.mymoviestracker.ui

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
import kotlinx.android.synthetic.main.rv_movies_item.view.*
import java.util.*

class MoviesRVAdapter : RecyclerView.Adapter<MoviesRVAdapter.MovieViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.rv_movies_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
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
            tvYear.text = Calendar.getInstance()
                .apply { time = movie.release_date }
                .get(Calendar.YEAR).toString()
            tvOverview.text = movie.overview
            GlideApp.with(this)
                .load(Constants.TMDB_IMAGE_BASE + movie.posterPath)
                .into(imgPoster)
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