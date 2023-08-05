package com.sixbits.picsapp.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sixbits.picsapp.databinding.FragmentAlbumsListBinding

class AlbumListFragment : Fragment() {

    // We are doing it this way to avoid memory leaks, late init vars can be used in some situations though
    private var _binding: FragmentAlbumsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}