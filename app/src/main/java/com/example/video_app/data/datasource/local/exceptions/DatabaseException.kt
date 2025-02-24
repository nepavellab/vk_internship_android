package com.example.video_app.data.datasource.local.exceptions

/**
 * Базовый класс исключений для всех ошибок, связанных с базой данных.
 *
 * @property message сообщение об ошибке.
 */
open class DatabaseException(
    override val message: String
) : Exception(message)