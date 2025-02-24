package com.example.video_app.domain.usecase

import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.domain.repository.Repository
import javax.inject.Inject

/**
 * UseCase для сохранения видео к кэш.
 *
 * Метод [invoke] загружает список видео для указанной страницы.
 *
 * @property repository Общий репозиторий для доступа к данным.
 */
class SaveVideosToCacheUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Метод сохраняет видео в локальное хранилище.
     *
     * @param videos список видео для сохранения.
     */
    suspend operator fun invoke(videos: List<VideoItem>) {
        repository.saveDataToCache(videos = videos)
    }
}