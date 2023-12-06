package com.lovevery.notes.android

import android.app.Application
import android.content.Context
import com.lovevery.notes.android.di.component.AppComponent
import com.lovevery.notes.android.di.component.DaggerAppComponent
import com.lovevery.notes.android.di.module.AppModule

class LoveveryNotesApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}