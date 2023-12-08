package com.lovevery.notes.android.ui

import com.lovevery.notes.android.model.Note
import com.lovevery.notes.android.model.Notes
import com.lovevery.notes.android.model.UserNotes

sealed class NotesState {
    data class Error(val throwable: Throwable) : NotesState()
    object Empty : NotesState()
    data class Success(val notes: Notes) : NotesState()
}

sealed class UserNotesState {
    data class Error(val throwable: Throwable) : UserNotesState()
    object Empty : UserNotesState()
    data class Success(val notes: UserNotes) : UserNotesState()
}

sealed class PostNoteState {
    data class Error(val throwable: Throwable) : PostNoteState()
    data class Success(val note: Note) : PostNoteState()
}
