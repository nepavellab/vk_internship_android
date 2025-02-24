package com.example.video_app.di.modules

import android.content.Context
import androidx.room.Room
import com.example.video_app.data.datasource.local.CashDao
import com.example.video_app.data.datasource.local.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    companion object {
        private const val DATABASE_NAME = "app-database"
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            context, ApplicationDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCashDao(appDatabase: ApplicationDatabase): CashDao {
        return appDatabase.cashDao()
    }
}