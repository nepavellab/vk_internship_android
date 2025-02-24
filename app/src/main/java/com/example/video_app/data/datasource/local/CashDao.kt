package com.example.video_app.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.video_app.data.entity.databasemodels.TABLE_NAME
import com.example.video_app.data.entity.databasemodels.VideoCache

@Dao
interface CashDao {
    /**
     * Метод постранично выгружает видео из локального хранилища.
     *
     * @return возвращает список всех представлений типа [VideoCash].
     */
    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getVideosFromCachePerPage(): List<VideoCache>

    /**
     * Метод сохраняет видео в локальное хранилище.
     *
     * @param videos список видео, который требуется сохранить.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveVideosToCash(videos: List<VideoCache>)

    /**
     * Метод очищает всю таблицу базы данных.
     */
    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllVideos()
}