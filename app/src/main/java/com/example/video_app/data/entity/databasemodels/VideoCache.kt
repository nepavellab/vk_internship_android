package com.example.video_app.data.entity.databasemodels

import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val TABLE_NAME = "network_cash"

@Entity(tableName = TABLE_NAME)
data class VideoCache(
    @PrimaryKey val videoId: String,
    val name: String,
    val thumbnail: String,
    val duration: String,
    val description: String,
    val mp4Url: String
)
