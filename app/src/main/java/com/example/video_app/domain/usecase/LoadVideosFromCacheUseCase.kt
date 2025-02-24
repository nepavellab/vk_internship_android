package com.example.video_app.domain.usecase

import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.domain.repository.Repository
import javax.inject.Inject

/**
 * UseCase для загрузки видео из локального кэша.
 *
 * Метод [invoke] загружает список видео из базы данных.
 *
 * @property repository Общий репозиторий для доступа к данным.
 */
class LoadVideosFromCacheUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Загружает видео из сети для указанной страницы.
     *
     * @return В случае пустого кэша возвращает [Result] с исключением [LoadCacheException].
     * В случае успеха возвращает [Result] со списком видео [VideoItem].
     */
    suspend operator fun invoke(): Result<List<VideoItem>> {
        return repository.loadDataFromCache().fold(
            ifLeft = { loadCacheException ->
                Result.failure(loadCacheException)
            },
            ifRight =  { videos ->
                Result.success(videos)
            }
        )
    }
}