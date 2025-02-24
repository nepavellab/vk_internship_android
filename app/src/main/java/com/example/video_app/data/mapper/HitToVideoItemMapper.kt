package com.example.video_app.data.mapper

import com.example.video_app.data.entity.networkmodels.Hit
import com.example.video_app.domain.entity.VideoItem

class HitToVideoItemMapper : Mapper<Hit, VideoItem> {
    override operator fun invoke(input: Hit): VideoItem {
        with(input) {
            return VideoItem(
                videoId = videoId,
                name = title,
                thumbnail = thumbnail,
                duration = duration,
                description = description,
                mp4Url = urls.mp4
            )
        }
    }
}