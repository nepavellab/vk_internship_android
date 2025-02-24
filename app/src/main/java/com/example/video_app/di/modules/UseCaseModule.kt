package com.example.video_app.di.modules

import com.example.video_app.domain.repository.Repository
import com.example.video_app.domain.usecase.ClearCacheUseCase
import com.example.video_app.domain.usecase.LoadVideosFromCacheUseCase
import com.example.video_app.domain.usecase.LoadVideosFromNetworkUseCase
import com.example.video_app.domain.usecase.SaveVideosToCacheUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideLoadVideosFromNetworkUseCase(
        repository: Repository
    ): LoadVideosFromNetworkUseCase {
        return LoadVideosFromNetworkUseCase(repository)
    }

    @Provides
    fun provideLoadVideosFromCacheUseCase(
        repository: Repository
    ): LoadVideosFromCacheUseCase {
        return LoadVideosFromCacheUseCase(repository)
    }

    @Provides
    fun provideSaveVideosToCacheUseCase(
        repository: Repository
    ): SaveVideosToCacheUseCase {
        return SaveVideosToCacheUseCase(repository)
    }

    @Provides
    fun provideClearCacheUseCase(
        repository: Repository
    ): ClearCacheUseCase {
        return ClearCacheUseCase(repository)
    }
}