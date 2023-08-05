package com.sixbits.picsapp.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sixbits.domain.model.Album
import com.sixbits.picsapp.databinding.RowAlbumListItemBinding

class AlbumsAdapter : ListAdapter<AlbumWithPhotos, AlbumsAdapter.VH>(AlbumsDiffUtl()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowAlbumListItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(currentList[position])
    }

    class AlbumsDiffUtl : DiffUtil.ItemCallback<AlbumWithPhotos>() {
        override fun areContentsTheSame(oldItem: AlbumWithPhotos, newItem: AlbumWithPhotos): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: AlbumWithPhotos, newItem: AlbumWithPhotos): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class VH(private val binding: RowAlbumListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumWithPhotos) {
            Glide.with(binding.root)
                .load(album.thumbnailUrl)
                .into(binding.albumImage)

            binding.albumTitle.text = album.title
        }
    }
}