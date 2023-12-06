package com.lovevery.notes.android.data.repository.mapper

import com.lovevery.notes.android.data.api.model.NoteResponse
import com.lovevery.notes.android.data.api.model.NotesResponse
import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.data.repository.model.NotesModel

fun NoteResponse.toNoteModel(): NoteModel = NoteModel(
    subject = this.subject,
    content = this.content
)

fun NotesResponse.toNotesModel(): NotesModel = NotesModel(
    notes = this.notes.mapValues { (_, noteModelResponses) ->
        noteModelResponses.map { noteModelResponse ->
            NoteModel(
                subject = noteModelResponse.subject,
                content = noteModelResponse.content
            )
        }
    }
)