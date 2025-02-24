package com.example.video_app.presentation.main_screen.screenstate

/**
 * Перечисление возможных способов добавления видео на экран.
 */
enum class UploadModes {
    /**
     * Добавить видео к существующим.
     */
    APPEND,

    /**
     * Обновить ленту видео новыми.
     */
    RESET
}