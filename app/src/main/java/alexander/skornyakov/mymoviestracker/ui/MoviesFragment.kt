package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.ui.adapters.MoviesRVAdapter
import alexander.skornyakov.mymoviestracker.viewmodels.MainViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies){

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            MoviesRVAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        mainViewModel.loadMovies()

        mainViewModel.movies.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.submitList(it)
            }
        })

        etSearch.doOnTextChanged { text, start, before, count ->
            text?.let {
                mainViewModel.loadMovies(text.toString())
            }
        }

        adapter.setWatchingListener {
            mainViewModel.addToWatching(it)
            Toast.makeText(
                context,
                "${it.title} is being added...",
                Toast.LENGTH_LONG)
                .show()
        }

        adapter.setWatchedListener {
            mainViewModel.addToWatched(it)
            Toast.makeText(
                context,
                "${it.title} is being added...",
                Toast.LENGTH_LONG)
                .show()
        }

    }
}