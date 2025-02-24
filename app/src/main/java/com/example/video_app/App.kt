package com.example.video_app

import android.app.Application
import com.example.video_app.di.AppComponent
import com.example.video_app.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .context(context = this)
            .build()
    }
}