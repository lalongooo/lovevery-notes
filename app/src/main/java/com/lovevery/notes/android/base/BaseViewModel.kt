package com.lovevery.notes.android.base

import androidx.lifecycle.ViewModel
import com.lovevery.notes.android.utility.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    val progress = SingleLiveEvent<Boolean>()

    override fun onCleared() {
        disposable.clear()
    }
}