package com.example.video_app.data.mapper

import com.example.video_app.data.entity.databasemodels.VideoCache
import com.example.video_app.domain.entity.VideoItem

class VideoCacheToVideoItemMapper: Mapper<VideoCache, VideoItem> {
    override fun invoke(input: VideoCache): VideoItem {
        with(input) {
            return VideoItem(
                videoId = videoId,
                name = name,
                duration = duration,
                description = description,
                mp4Url = mp4Url,
                thumbnail = thumbnail
            )
        }
    }
}