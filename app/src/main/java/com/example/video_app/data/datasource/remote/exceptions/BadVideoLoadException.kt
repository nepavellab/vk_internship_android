package com.example.video_app.data.datasource.remote.exceptions

/**
 * Исключение при загрузке видео из сети.
 *
 * @property message сообщение об ошибке.
 */
class BadVideoLoadException(
    override val message: String
) : NetworkException(message)