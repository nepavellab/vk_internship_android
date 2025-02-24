package com.example.video_app.data.datasource.remote.exceptions

/**
 * Базовый класс исключений для всех ошибок, связанных с сетью.
 *
 * @property message сообщение об ошибке.
 */
open class NetworkException(
    override val message: String
) : Exception(message)