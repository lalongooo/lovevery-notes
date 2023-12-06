package com.lovevery.notes.android.mapper

import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.data.repository.model.NotesModel
import com.lovevery.notes.android.model.Note
import com.lovevery.notes.android.model.Notes

fun NoteModel.toNote(): Note {
    return Note(
        subject = this.subject,
        content = this.content
    )
}

fun NotesModel.toNotes(): Notes = Notes(
    notes = this.notes.mapValues { (_, noteModelResponses) ->
        noteModelResponses.map { it.toNote() }
    }
)
