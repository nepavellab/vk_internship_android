package com.example.video_app.presentation.main_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.video_app.domain.usecase.ClearCacheUseCase
import com.example.video_app.domain.usecase.LoadVideosFromCacheUseCase
import com.example.video_app.domain.usecase.LoadVideosFromNetworkUseCase
import com.example.video_app.domain.usecase.SaveVideosToCacheUseCase
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel главного экрана для корректной иницализации модели через Dagger.
 *
 * @property loadVideosFromNetworkUseCase UseCase для загрузки данных из сети.
 * @property loadVideosFromCashUseCase UseCase для загрузки кэшированных данных.
 * @property saveVideosToCacheUseCase UseCase для сохранения загруженных видео в кэш.
 * @property clearCacheUseCase UseCase для очистки кэша.
 */
class MainScreenViewModelFactory @Inject constructor(
    private val loadVideosFromNetworkUseCase: LoadVideosFromNetworkUseCase,
    private val loadVideosFromCacheUseCase: LoadVideosFromCacheUseCase,
    private val saveVideosToCacheUseCase: SaveVideosToCacheUseCase,
    private val clearCacheUseCase: ClearCacheUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(
            loadVideosFromNetworkUseCase = loadVideosFromNetworkUseCase,
            loadVideosFromCacheUseCase = loadVideosFromCacheUseCase,
            saveVideosToCacheUseCase = saveVideosToCacheUseCase,
            clearCacheUseCase = clearCacheUseCase
        ) as T
    }
}