package com.example.video_app.data.entity.networkmodels

import com.google.gson.annotations.SerializedName

data class Hit(
    val title: String,
    val description: String,
    val tags: List<String>,
    val downloads: Int,
    @SerializedName("is_vertical") val isVertical: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("base_filename") val baseFilename: String,
    val poster: String,
    val thumbnail: String,
    val state: String,
    val views: Int,
    val likes: Int,
    @SerializedName("contributor_id") val contributorId: String?,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("playback_id") val playbackId: String,
    @SerializedName("aspect_ratio") val aspectRatio: String,
    val duration: String,
    @SerializedName("max_height") val maxHeight: Int,
    @SerializedName("max_width") val maxWidth: Int,
    @SerializedName("video_id") val videoId: String,
    val id: String,
    @SerializedName("published_at_timestamp") val publishedAtTimestamp: Long,
    @SerializedName("d2v_ratio") val d2vRatio: Double,
    @SerializedName("downloads_last_month") val downloadsLastMonth: Int,
    val scene: String?,
    val urls: VideoUrls,
    @SerializedName("blur_hash") val blurHash: String,
    @SerializedName("is_premium") val isPremium: Boolean,
    val fps: Int,
    @SerializedName("is_ai_generated") val isAiGenerated: Boolean,
    val slug: String,
    val objectID: String
)