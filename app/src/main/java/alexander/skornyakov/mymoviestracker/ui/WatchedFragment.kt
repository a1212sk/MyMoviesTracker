package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.ui.adapters.WatchingMoviesRVAdapter
import alexander.skornyakov.mymoviestracker.viewmodels.WatchedViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_watched.*

@AndroidEntryPoint
class WatchedFragment : Fragment(R.layout.fragment_watched){

    val viewModel : WatchedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            WatchingMoviesRVAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.watchedMovies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}