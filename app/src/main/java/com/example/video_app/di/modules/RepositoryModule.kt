package com.example.video_app.di.modules

import com.example.video_app.data.datasource.local.CashDao
import com.example.video_app.data.datasource.local.LocalSource
import com.example.video_app.data.datasource.local.LocalSourceImpl
import com.example.video_app.data.datasource.remote.NetworkApiService
import com.example.video_app.data.datasource.remote.RemoteSource
import com.example.video_app.data.datasource.remote.RemoteSourceImpl
import com.example.video_app.data.mapper.HitToVideoItemMapper
import com.example.video_app.data.mapper.VideoCacheToVideoItemMapper
import com.example.video_app.data.mapper.VideoItemToVideoCacheMapper
import com.example.video_app.data.repository.RepositoryImpl
import com.example.video_app.domain.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteSource,
        localDataSource: LocalSource,
        hitToVideoItemMapper: HitToVideoItemMapper,
        videoItemToVideoCacheMapper: VideoItemToVideoCacheMapper,
        vCacheToVItemMapper: VideoCacheToVideoItemMapper
    ): Repository {
        return RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            hitToVideoItemMapper = hitToVideoItemMapper,
            vItemToVCacheMapper = videoItemToVideoCacheMapper,
            vCacheToVItemMapper = vCacheToVItemMapper
        )
    }

    @Provides
    fun provideRemoteDataSource(
        networkApi: NetworkApiService
    ): RemoteSource {
        return RemoteSourceImpl(
            networkApi = networkApi
        )
    }

    @Provides
    fun provideLocalDataSource(cashDao: CashDao): LocalSource {
        return LocalSourceImpl(videoCashDao = cashDao)
    }
}