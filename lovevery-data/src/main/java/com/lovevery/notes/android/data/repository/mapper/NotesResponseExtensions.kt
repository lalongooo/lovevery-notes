package com.lovevery.notes.android.data.repository.mapper

import com.lovevery.notes.android.data.api.model.NotesResponse
import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel

fun NotesResponse.toUserNotesModel(): UserNotesModel = UserNotesModel(
    notes = this.notes.mapValues { (_, noteModelResponses) ->
        noteModelResponses.map { noteModelResponse ->
            NoteModel(
                subject = noteModelResponse.subject,
                content = noteModelResponse.message
            )
        }
    }
)