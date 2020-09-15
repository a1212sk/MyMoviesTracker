package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.ui.adapters.WatchingMoviesRVAdapter
import alexander.skornyakov.mymoviestracker.viewmodels.WatchingViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_watching.*

@AndroidEntryPoint
class WatchingFragment : Fragment(R.layout.fragment_watching){

     val viewModel : WatchingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            WatchingMoviesRVAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.watchingMovies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}