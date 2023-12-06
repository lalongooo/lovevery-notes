package com.lovevery.notes.android.extensions

import androidx.fragment.app.Fragment
import com.lovevery.notes.android.LoveveryNotesApplication
import com.lovevery.notes.android.di.component.AppComponent

fun Fragment.getAppComponent(): AppComponent =
    (requireContext().applicationContext as LoveveryNotesApplication).appComponent