package com.lovevery.notes.android.ui.notes

import com.lovevery.notes.android.base.BaseViewModel
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.extensions.asLiveData
import com.lovevery.notes.android.mapper.toNote
import com.lovevery.notes.android.ui.PostNoteState
import com.lovevery.notes.android.utility.SingleLiveEvent
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseViewModel() {

    private val _postNoteState = SingleLiveEvent<PostNoteState>()
    val postNoteState = _postNoteState.asLiveData()

    fun postNote(content: String) {
        disposable.add(
            notesRepository.addNote(
                subject = "cars", // TODO: Get this from a SubjectManager or a session storage
                content = content,
            )
                .map { it.toNote() }
                .doOnSubscribe { progress.value = true }
                .doFinally { progress.value = false }
                .subscribe(
                    { note ->
                        if (note.subject.isNotEmpty()) {
                            _postNoteState.value = PostNoteState.Success(note)
                        }
                    },
                    {
                        _postNoteState.value = PostNoteState.Error(it)
                    }
                )
        )
    }
}
