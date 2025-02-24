package com.example.video_app.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoItem(
    val videoId: String,
    val name: String,
    val thumbnail: String,
    val duration: String,
    val description: String,
    val mp4Url: String
) : Parcelable