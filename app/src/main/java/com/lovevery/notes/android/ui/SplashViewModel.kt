package com.lovevery.notes.android.ui

import com.lovevery.notes.android.base.BaseViewModel
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.extensions.asLiveData
import com.lovevery.notes.android.mapper.toNotes
import com.lovevery.notes.android.utility.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseViewModel() {

    private val _notesState = SingleLiveEvent<NotesState>()
    val notesState = _notesState.asLiveData()

    fun getNotes() {
        disposable.add(
            notesRepository.getNotes()
                .map { it.toNotes() }
                .doOnSubscribe { progress.value = true }
                .doFinally { progress.value = false }
                .subscribe(
                    {notes ->
                        if(notes.notes.isEmpty()) {
                            _notesState.value = NotesState.Empty
                        } else {
                            _notesState.value = NotesState.NotEmpty(notes)
                        }
                    },
                    {
                        _notesState.value = NotesState.Error(it)
                    }
                )
        )
    }
}