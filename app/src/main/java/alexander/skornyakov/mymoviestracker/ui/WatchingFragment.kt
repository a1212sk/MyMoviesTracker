package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.helpers.hide
import alexander.skornyakov.mymoviestracker.helpers.show
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

        progressBar2.show()
        recyclerView.hide()
        viewModel.watchingMovies.observe(viewLifecycleOwner, Observer {
            progressBar2.hide()
            recyclerView.show()
            adapter.submitList(it)
        })
    }

}