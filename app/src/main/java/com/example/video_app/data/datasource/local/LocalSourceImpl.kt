package com.example.video_app.data.datasource.local

import com.example.video_app.data.datasource.local.exceptions.EmptyCacheException
import com.example.video_app.data.entity.databasemodels.VideoCache
import javax.inject.Inject

/**
 * Имплементация локального репозитория.
 * Представляет базу данных для кэширования загруженных видео.
 */
class LocalSourceImpl @Inject constructor(
    private val videoCashDao: CashDao
) : LocalSource {
    override suspend fun saveVideosToCache(videos: List<VideoCache>) {
        videoCashDao.saveVideosToCash(videos = videos)
    }

    override suspend fun loadVideosFromCache(): List<VideoCache> {
            // загружаем сохранённые видео из кэша
            val videosFromCache = videoCashDao.getVideosFromCachePerPage()

            if (videosFromCache.isEmpty()) {
                // если кэш пустой
                throw EmptyCacheException("Нет сохранённых данных")
            }
            return videosFromCache
    }

    override suspend fun clearCache() {
        videoCashDao.deleteAllVideos()
    }
}