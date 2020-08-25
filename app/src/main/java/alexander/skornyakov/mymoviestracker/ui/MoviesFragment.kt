package alexander.skornyakov.mymoviestracker.ui

import alexander.skornyakov.mymoviestracker.R
import alexander.skornyakov.mymoviestracker.viewmodels.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(R.layout.fragment_movies){

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviesRVAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        mainViewModel.getPopularMovies().observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.submitList(it.results)
            }
        })

    }
}