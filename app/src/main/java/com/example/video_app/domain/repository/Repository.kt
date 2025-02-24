package com.example.video_app.domain.repository

import arrow.core.Either
import com.example.video_app.data.datasource.local.exceptions.DatabaseException
import com.example.video_app.data.datasource.remote.exceptions.NetworkException
import com.example.video_app.data.entity.networkmodels.Hit
import com.example.video_app.domain.entity.VideoItem

interface Repository {
    /**
     * Метод загружает список видео из сети.
     *
     * @param countToLoad количество видео для загрузки за один запрос.
     * @param currentPageNumber Номер текущей страницы для пагинации.
     * @return Возвращает [Either],
     * содержащий либо исключение [NetworkException] в случае ошибки,
     * либо список [Hit] в случае успешной загрузки видео.
     */
    suspend fun loadDataFromNetwork(
        countToLoad: Int, currentPageNumber: Int
    ): Either<NetworkException, List<VideoItem>>

    /**
     * Метод сохраняет данные в кэш.
     *
     * @param videos список видео для записи в кэш.
     */
    suspend fun saveDataToCache(videos: List<VideoItem>)

    /**
     * Метод постранично выгружает данные из кэша.
     *
     * @return Возвращает [Either],
     * содержащий либо исключение [DatabaseException] в случае ошибки,
     * либо список [VideoItem] в случае успешной загрузки видео.
     */
    suspend fun loadDataFromCache():
            Either<DatabaseException, List<VideoItem>>

    /**
     * Метод сбрасывает кэшированные записи в локальном хранилище.
     */
    suspend fun clearCache()
}