package com.example.video_app.di

import android.content.Context
import com.example.video_app.di.modules.DatabaseModule
import com.example.video_app.di.modules.MapperModule
import com.example.video_app.di.modules.NetworkModule
import com.example.video_app.di.modules.RepositoryModule
import com.example.video_app.di.modules.UseCaseModule
import com.example.video_app.presentation.main_screen.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    RepositoryModule::class,
    UseCaseModule::class,
    MapperModule::class,
    DatabaseModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}