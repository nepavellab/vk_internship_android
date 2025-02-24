package com.example.video_app.domain.usecase

import com.example.video_app.domain.repository.Repository
import javax.inject.Inject

/**
 * UseCase для очистки кэша.
 *
 * Метод [invoke] выполняет очистку кэша локального хранилища.
 */
class ClearCacheUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Очищает локальное хранилище.
     */
    suspend operator fun invoke() {
        repository.clearCache()
    }
}