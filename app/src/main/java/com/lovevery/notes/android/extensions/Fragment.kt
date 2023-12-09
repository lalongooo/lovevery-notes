package com.lovevery.notes.android.extensions

import android.content.Context
import com.lovevery.notes.android.LoveveryNotesApplication
import com.lovevery.notes.android.di.component.AppComponent

fun Context.getAppComponent(): AppComponent = (applicationContext as LoveveryNotesApplication).appComponent
