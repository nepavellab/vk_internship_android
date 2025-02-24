package com.example.video_app.data.entity.networkmodels

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val page: Int,
    val pages: Int,
    @SerializedName("page_size") val pageSize: Int,
    val params: String,
    val total: Int,
    val hits: List<Hit>
)
