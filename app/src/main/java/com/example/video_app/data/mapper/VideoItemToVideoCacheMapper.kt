package com.example.video_app.data.mapper

import com.example.video_app.data.entity.databasemodels.VideoCache
import com.example.video_app.domain.entity.VideoItem

class VideoItemToVideoCacheMapper : Mapper<VideoItem, VideoCache> {
    override fun invoke(input: VideoItem): VideoCache {
        with (input) {
            return VideoCache(
                videoId = videoId,
                name = name,
                thumbnail = thumbnail,
                duration = duration,
                description = description,
                mp4Url = mp4Url
            )
        }
    }
}