package com.example.video_app.data.datasource.local.exceptions

/**
 * Исключение выбрасывается в случае, когда кэш пустой.
 *
 * @property message сообщение об ошибке.
 */
class EmptyCacheException(
    override val message: String
) : DatabaseException(message)