package com.example.video_app.data.datasource.remote

import com.example.video_app.data.entity.networkmodels.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Интерфейс сетевого API ресурса Coverr.
 */
interface NetworkApiService {
    /**
     * Метод загрузки данных из сети с ресурса Coverr
     *
     * @param apiKey ключ авторизации для API
     * @param page номер текущей страницы, с которой загружаются видео
     * @param pageSize число видео, загружаемых на одной странице
     * @param urls логический параметр:
     * [true]: загружать сами видео .mp4
     * [false]: загружать только информацию о видео без .mp4
     */
    @GET("videos")
    suspend fun loadVideos(
        @Header("Authorization") apiKey: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("urls") urls: Boolean = true
    ): Response<ApiResponse>
}