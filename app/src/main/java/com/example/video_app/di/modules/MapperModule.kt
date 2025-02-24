package com.example.video_app.di.modules

import com.example.video_app.data.mapper.HitToVideoItemMapper
import com.example.video_app.data.mapper.VideoCacheToVideoItemMapper
import com.example.video_app.data.mapper.VideoItemToVideoCacheMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {
    @Provides
    fun provideHitsToVideoItemMapper(): HitToVideoItemMapper {
        return HitToVideoItemMapper()
    }

    @Provides
    fun provideVideoItemToVideoCacheMapper(): VideoItemToVideoCacheMapper {
        return VideoItemToVideoCacheMapper()
    }

    @Provides
    fun provideVideoCacheToVideoItemMapper(): VideoCacheToVideoItemMapper {
        return VideoCacheToVideoItemMapper()
    }
}