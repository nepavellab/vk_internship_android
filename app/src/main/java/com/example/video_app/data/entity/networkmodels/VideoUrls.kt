package com.example.video_app.data.entity.networkmodels

import com.google.gson.annotations.SerializedName

data class VideoUrls(
    val mp4: String,
    @SerializedName("mp4_preview") val mp4Preview: String,
    @SerializedName("mp4_download") val mp4Download: String
)