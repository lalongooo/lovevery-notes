package com.lovevery.notes.android.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lovevery.notes.android.MainActivityViewModel
import com.lovevery.notes.android.di.ViewModelProviderFactory
import com.lovevery.notes.android.di.key.ViewModelKey
import com.lovevery.notes.android.ui.SplashViewModel
import com.lovevery.notes.android.ui.notes.NotesViewModel
import com.lovevery.notes.android.ui.users.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindUsersViewModel(
        loginViewModel: UsersViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(
        loginViewModel: SplashViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    abstract fun bindNotesViewModel(
        loginViewModel: NotesViewModel
    ): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(
        mainActivityViewModel: MainActivityViewModel
    ): ViewModel
}
