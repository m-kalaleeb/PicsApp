package com.sixbits.picsapp.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sixbits.picsapp.databinding.FragmentAlbumsListBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun setupListeners() {
        viewModel.albums.observe(viewLifecycleOwner) {
            albumsAdapter.submitList(it)
        }
    }
}