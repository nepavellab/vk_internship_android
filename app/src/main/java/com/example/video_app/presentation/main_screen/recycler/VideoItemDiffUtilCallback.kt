package com.example.video_app.presentation.main_screen.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.video_app.domain.entity.VideoItem

class VideoItemDiffUtilCallback(
    private val oldList: List<VideoItem>,
    private val newList: List<VideoItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].videoId == newList[newItemPosition].videoId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}