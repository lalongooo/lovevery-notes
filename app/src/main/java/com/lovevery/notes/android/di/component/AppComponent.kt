package com.lovevery.notes.android.di.component

import com.lovevery.notes.android.data.api.ApiService
import com.lovevery.notes.android.data.di.module.NetworkModule
import com.lovevery.notes.android.di.ViewModelProviderFactory
import com.lovevery.notes.android.di.module.AppModule
import com.lovevery.notes.android.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
    ],
)
interface AppComponent {
    fun viewModelsFactory(): ViewModelProviderFactory

    fun loveveryApiService(): ApiService
}
