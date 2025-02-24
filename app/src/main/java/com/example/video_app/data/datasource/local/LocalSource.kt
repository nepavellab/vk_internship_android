package com.example.video_app.data.datasource.local

import com.example.video_app.data.datasource.local.exceptions.DatabaseException
import com.example.video_app.data.entity.databasemodels.VideoCache

/**
 * Интерфейс локального репозитория.
 */
interface LocalSource {
    /**
     * Метод кэширования видео (запись их в базу данных).
     *
     * @param videos список видео для сохранения в базу данных.
     */
    suspend fun saveVideosToCache(videos: List<VideoCache>)

    /**
     * Метод постранично выгружает данные из кэша.
     *
     * @throws [DatabaseException]
     * @return Список [VideoCache] в случае успешной загрузки видео.
     */
    suspend fun loadVideosFromCache(): List<VideoCache>

    /**
     * Метод очищает все накопленные записи в таблице.
     */
    suspend fun clearCache()
}