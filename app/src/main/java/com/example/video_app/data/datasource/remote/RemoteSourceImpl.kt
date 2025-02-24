package com.example.video_app.data.datasource.remote

import com.example.video_app.BuildConfig
import com.example.video_app.data.datasource.remote.exceptions.BadVideoLoadException
import com.example.video_app.data.entity.networkmodels.Hit
import javax.inject.Inject

/**
 * Имплементация удалённого репозитория.
 * Представляет интернет ресурс для загрузки видео через открытый API.
 */
class RemoteSourceImpl @Inject constructor(
    private val networkApi: NetworkApiService
) : RemoteSource {

    override suspend fun loadVideosFromNetwork(
        countToLoad: Int,
        currentPageNumber: Int
    ): List<Hit> {
        // загружаем видео через API
        try {
            val response = networkApi.loadVideos(
                apiKey = BuildConfig.API_KEY,
                page = currentPageNumber,
                pageSize = countToLoad
            )

            // если видео загрузить не удалось, то выбрасываем исключение
            return response.body()!!.hits
        }
        catch (exception: Exception) {
            throw BadVideoLoadException("Не удалось загрузить данные из сети")
        }
    }
}