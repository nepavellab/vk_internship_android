package com.example.video_app.presentation.main_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.video_app.domain.usecase.ClearCacheUseCase
import com.example.video_app.domain.usecase.LoadVideosFromCacheUseCase
import com.example.video_app.domain.usecase.LoadVideosFromNetworkUseCase
import com.example.video_app.domain.usecase.SaveVideosToCacheUseCase
import com.example.video_app.presentation.main_screen.screenstate.ErrorVisibility
import com.example.video_app.presentation.main_screen.screenstate.ScreenState
import com.example.video_app.presentation.main_screen.screenstate.UploadModes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * View Model главного экрана.
 *
 * @property loadVideosFromNetworkUseCase UseCase для загрузки данных из сети.
 * @property loadVideosFromCashUseCase UseCase для загрузки кэшированных данных.
 * @property saveVideosToCacheUseCase UseCase для сохранения загруженных видео в кэш.
 * @property clearCacheUseCase UseCase для очистки кэша.
 * @property paginationCounter номер страницы для сетевого API загружаемых видео.
 * @property _screenState изменяемое состояние экрана.
 * @property screenState состояние экрана для чтения в UI.
 */
class MainScreenViewModel(
    private val loadVideosFromNetworkUseCase: LoadVideosFromNetworkUseCase,
    private val loadVideosFromCacheUseCase: LoadVideosFromCacheUseCase,
    private val saveVideosToCacheUseCase: SaveVideosToCacheUseCase,
    private val clearCacheUseCase: ClearCacheUseCase
) : ViewModel() {

    private var paginationCounter = 1
    private val videosPerPage = 5
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Init)
    val screenState: StateFlow<ScreenState>
        get() = _screenState

    /**
     * Метод предоставляет первичную загрузку видео.
     * Использовать следует только при первом открытии экрана (состояние [ScreenState.Init]).
     */
    fun provideVideos() = viewModelScope.launch {
        _screenState.value = ScreenState.Loading

        cacheCheck(UploadModes.RESET)

        if (_screenState.value !is ScreenState.Success) { // если загрузить кэш не удалось
            // загружаем видео из сети
            loadFromNet(UploadModes.RESET)

            // если данные не загрузились
            if (_screenState.value is ScreenState.Error) {
                _screenState.value = ScreenState.Empty
            }
        }
    }

    // загрузка данных из сети
    private suspend fun loadFromNet(mode: UploadModes) {
        // загружаем видео из сети
        val videosFromNetwork = loadVideosFromNetworkUseCase(
            videosCountToLoad = videosPerPage,
            currentPageNumber = paginationCounter
        )

        if (videosFromNetwork.isSuccess) { // если видео из сети успешно загружены
            val loadedVideos = videosFromNetwork.getOrDefault(emptyList())

            // кэшируем загруженные видео
            saveVideosToCacheUseCase(videos = loadedVideos)

            // добавляем видео на экран
            _screenState.value = ScreenState.Success(
                videos = loadedVideos, addMode =  mode
            )
        } else {
            val networkLoadErrorMessage = videosFromNetwork.exceptionOrNull()!!.message
            // выводим на экран сообщение об ошибке
            _screenState.value = ScreenState.Error(
                errorMessage = networkLoadErrorMessage ?: "",
                isShown = ErrorVisibility.SHOW
            )
        }
    }

    // Загрузка данных из кэша
    private suspend fun cacheCheck(mode: UploadModes) {
        // скачиваем видео из кэша
        val videosFromCache = loadVideosFromCacheUseCase()

        if (videosFromCache.isSuccess) { // если видео из кэша успешно загружены
            _screenState.value = ScreenState.Success(
                videos = videosFromCache.getOrDefault(emptyList()),
                addMode = mode
            )
        } else {
            // переключаем состояние на ошибку
            _screenState.value = ScreenState.Error(
                errorMessage = "",
                isShown = ErrorVisibility.HIDE
            )
        }
    }

    /**
     * Метод для дозагрузки данных из сети во время прокрутки списка.
     */
    fun uploadNewVideos() = viewModelScope.launch {
        _screenState.value = ScreenState.Loading
        loadFromNet(mode = UploadModes.APPEND)
    }

    /**
     * Метод выполняет удаление кэшированных видео из локального хранилища и очищает ленту.
     */
    fun clearCache() = viewModelScope.launch {
        // очищаем кэш
        clearCacheUseCase()

        // сбрасываем все видео в ленте
        _screenState.value = ScreenState.Success(
            videos = emptyList(),
            addMode = UploadModes.RESET
        )

        // отображаем пустое состояние
        _screenState.value = ScreenState.Empty
    }
}