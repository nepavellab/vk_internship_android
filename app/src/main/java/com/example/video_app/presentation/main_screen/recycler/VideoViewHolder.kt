package com.example.video_app.presentation.main_screen.recycler

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.video_app.R
import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.databinding.VideoItemBinding

class VideoViewHolder (
    private val binding: VideoItemBinding
) : ViewHolder(binding.root) {
    fun bind(
        video: VideoItem,
        openVideoClickListener: (VideoItem) -> Unit
    ) {
        with (binding) {
            tvVideoName.text = video.name
            tvDuration.text = video.duration
            btnPlayVideo.setOnClickListener {
                openVideoClickListener(video)
            }

            Glide.with(ivThumbnail.context)
                .load(video.thumbnail)
                .placeholder(R.drawable.defaul_video)
                .into(ivThumbnail)
        }
    }
}