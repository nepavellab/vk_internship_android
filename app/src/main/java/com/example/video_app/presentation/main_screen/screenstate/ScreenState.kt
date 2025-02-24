package com.example.video_app.presentation.main_screen.screenstate

import com.example.video_app.domain.entity.VideoItem

/**
 * Представление всех возможных состояний главного экрана.
 */
sealed interface ScreenState {
    /**
     * Состояние текущей загрузки данных.
     */
    data object Loading : ScreenState

    /**
     * Состояние успешной загрузки данных.
     *
     * @property videos список загруженных видео.
     * @property addMode способ добавления видео на экран.
     */
    data class Success(
        val videos: List<VideoItem>,
        val addMode: UploadModes
    ) : ScreenState

    /**
     * Состояние неудачной загрузки данных.
     *
     * @property errorMessage сообщение об ошибке.
     */
    data class Error(val errorMessage: String, val isShown: ErrorVisibility) : ScreenState

    /**
     * Состояние первого открытия экрана.
     * Необходимо для того, чтобы загрузка из сети не начиналась при повороте экрана.
     */
    data object Init : ScreenState

    /**
     * Состояния экрана, при котором не удалось загрузить данные из сети, а сохранённых видео нет.
     */
    data object Empty: ScreenState
}