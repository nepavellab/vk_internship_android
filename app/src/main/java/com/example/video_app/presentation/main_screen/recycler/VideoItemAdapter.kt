package com.example.video_app.presentation.main_screen.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.databinding.VideoItemBinding

/**
 * Адаптер для ленты видеороликов на главном экране.
 *
 * @property openVideoClickListener callback, открывающий видео при нажатии на кнопку.
 * @property videoList список видео, отображающихся в ленте.
 */
class VideoItemAdapter(
    private val openVideoClickListener: (VideoItem) -> Unit
) : RecyclerView.Adapter<VideoViewHolder>() {
    private var videoList: MutableList<VideoItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            VideoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Очищает список видео.
     */
    fun clearContent() = videoList.clear()

    /**
     * Устаналивает новый список видео.
     *
     * @param newVideos список новых видео.
     */
    fun setVideos(newVideos: List<VideoItem>) {
        val calculatedDiff = DiffUtil.calculateDiff(
            VideoItemDiffUtilCallback(videoList, newVideos)
        )
        videoList = newVideos.toMutableList()
        calculatedDiff.dispatchUpdatesTo(this)
    }

    /**
     * Добавляет видео к существующим.
     *
     * @param newVideos список новых видео, который добавится к текущему.
     */
    fun addVideos(newVideos: List<VideoItem>) {
        val calculatedDiff = DiffUtil.calculateDiff(
            VideoItemDiffUtilCallback(videoList, videoList + newVideos)
        )
        videoList.addAll(newVideos)
        calculatedDiff.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = videoList.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position], openVideoClickListener)
    }
}