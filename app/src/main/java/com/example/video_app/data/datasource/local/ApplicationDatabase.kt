package com.example.video_app.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.video_app.data.entity.databasemodels.VideoCache

@Database(entities = [VideoCache::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun cashDao(): CashDao
}