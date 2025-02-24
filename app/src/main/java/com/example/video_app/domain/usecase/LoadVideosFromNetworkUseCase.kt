package com.example.video_app.domain.usecase

import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.domain.repository.Repository
import java.util.Locale
import javax.inject.Inject

/**
 * UseCase для загрузки видео из сети.
 *
 * Метод [invoke] постранично загружает список видео из сети.
 *
 * @property repository Общий репозиторий для доступа к данным.
 */
class LoadVideosFromNetworkUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Метод загружает видео из сети.
     *
     * @param videosCountToLoad количество видео, которое нужно загрузить.
     * @param currentPageNumber номер страницы для загрузки.
     */
    suspend operator fun invoke(
        videosCountToLoad: Int, currentPageNumber: Int
    ): Result<List<VideoItem>> {
        return repository.loadDataFromNetwork(
            countToLoad = videosCountToLoad,
            currentPageNumber = currentPageNumber
        ).fold(
                ifLeft = { networkException ->
                    Result.failure(networkException)
                },
                ifRight = { videos ->
                    val formatVideoItems = videos.map { video ->
                        // преобразуем строку времени, пришедшего с сервера в секундах
                        val videosDuration = video.duration.toFloat().toLong()

                        val seconds = videosDuration % 60
                        val hours =  videosDuration / 3600
                        val minutes = (videosDuration % 3600) / 60

                        // возвращаем новый объект с форматированным временем
                        video.copy(
                            duration = String.format(
                                Locale.getDefault(),
                                "%02d:%02d:%02d",
                                hours, minutes, seconds)
                        )
                    }
                    Result.success(formatVideoItems)
                }
            )
    }
}