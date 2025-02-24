package com.example.video_app.data.datasource.remote

import com.example.video_app.data.entity.networkmodels.Hit

/**
 * Интерфейс удалённого репозитория.
 */
interface RemoteSource {
    /**
     * Метод загружает видео из сети.
     *
     * @param countToLoad количество видео для загрузки за один запрос.
     * @param currentPageNumber номер страницы.
     * @throws [NetworkException]
     * @return Список [Hit] в случае успешной загрузки видео.
     */
    suspend fun loadVideosFromNetwork(
        countToLoad: Int,
        currentPageNumber: Int
    ): List<Hit>
}