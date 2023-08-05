package com.sixbits.picsapp.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sixbits.picsapp.databinding.FragmentAlbumsListBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AlbumListFragment : Fragment() {

    private val albumsAdapter = AlbumsAdapter()

    // We are doing it this way to avoid memory leaks, late init vars can be used in some situations though
    private var _binding: FragmentAlbumsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlbumListFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsListBinding.inflate(layoutInflater, container, false)

        setupViews()
        setupListeners()

        return binding.root
    }

    private fun setupViews() {
        binding.albums.adapter = albumsAdapter

        binding.albums.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    val llm = binding.albums.layoutManager as LinearLayoutManager
                    Timber.d("${llm.findLastVisibleItemPosition()} || ${albumsAdapter.itemCount} || ${albumsAdapter.itemCount - llm.findLastVisibleItemPosition()}")
                    if (albumsAdapter.itemCount - llm.findLastVisibleItemPosition() < 3) {
                        viewModel.requestMore()
                    }
                }
            }
        })
    }

    private fun setupListeners() {
        viewModel.albums.observe(viewLifecycleOwner) {
            albumsAdapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = it
        }
    }
}