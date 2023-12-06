package com.lovevery.notes.android.ui

import com.lovevery.notes.android.model.Note
import com.lovevery.notes.android.model.Notes

sealed class NotesState {
    data class Error(val throwable: Throwable) : NotesState()
    object Empty : NotesState()
    data class NotEmpty(val notes: Notes) : NotesState()
}

sealed class UserNotesState {
    data class Error(val throwable: Throwable) : UserNotesState()
    object Empty : UserNotesState()
    data class NotEmpty(val notes: Notes) : UserNotesState()
}

sealed class PostNoteState {
    data class Error(val throwable: Throwable) : PostNoteState()
    data class Success(val note: Note) : PostNoteState()
}
