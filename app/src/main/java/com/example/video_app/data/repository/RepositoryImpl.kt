package com.example.video_app.data.repository

import arrow.core.Either
import com.example.video_app.data.datasource.local.exceptions.DatabaseException
import com.example.video_app.data.datasource.local.LocalSource
import com.example.video_app.data.datasource.remote.exceptions.NetworkException
import com.example.video_app.data.datasource.remote.RemoteSource
import com.example.video_app.data.datasource.local.exceptions.EmptyCacheException
import com.example.video_app.data.datasource.remote.exceptions.BadVideoLoadException
import com.example.video_app.data.mapper.HitToVideoItemMapper
import com.example.video_app.data.mapper.VideoCacheToVideoItemMapper
import com.example.video_app.data.mapper.VideoItemToVideoCacheMapper
import com.example.video_app.domain.entity.VideoItem
import com.example.video_app.domain.repository.Repository
import javax.inject.Inject

/**
 * Имплементация общего (глобального) репозитория.
 *
 * @property remoteDataSource удалённый репозиторий.
 * @property localDataSource локальный репозиторий.
 * @property hitToVideoItemMapper объект-маппер ([Hit] -> [VideoItem]).
 * @property vItemToVCacheMapper объект-маппер ([VideoItem] -> [VideoCache]).
 * @property vCacheToVItemMapper объект-маппер ([VideoCache] -> [VideoItem]).
 */
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteSource,
    private val localDataSource: LocalSource,
    private val hitToVideoItemMapper: HitToVideoItemMapper,
    private val vItemToVCacheMapper: VideoItemToVideoCacheMapper,
    private val vCacheToVItemMapper: VideoCacheToVideoItemMapper
) : Repository {

    override suspend fun loadDataFromNetwork(
        countToLoad: Int, currentPageNumber: Int
    ): Either<NetworkException, List<VideoItem>> {
        return Either.catch { // преобразуем видео в корректный формат
            remoteDataSource.loadVideosFromNetwork(
                countToLoad = countToLoad,
                currentPageNumber = currentPageNumber
            ).map { hit ->
                hitToVideoItemMapper(hit)
            }
        }.mapLeft { exception ->
            BadVideoLoadException(exception.message!!)
        }
    }

    override suspend fun saveDataToCache(videos: List<VideoItem>) {
        localDataSource.saveVideosToCache(
            videos.map { video -> // преобразуем видео в корректный формат
                vItemToVCacheMapper(video)
            }
        )
    }

    override suspend fun loadDataFromCache():
            Either<DatabaseException, List<VideoItem>> {
        return Either.catch { // преобразуем видео в корректный формат
            localDataSource.loadVideosFromCache().map { videoFromCache ->
                vCacheToVItemMapper(videoFromCache)
            }
        }.mapLeft { exception ->
            EmptyCacheException(exception.message!!)
        }
    }

    override suspend fun clearCache() {
        localDataSource.clearCache()
    }
}