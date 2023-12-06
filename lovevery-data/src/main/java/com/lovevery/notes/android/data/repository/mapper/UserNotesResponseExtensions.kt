package com.lovevery.notes.android.data.repository.mapper

import com.lovevery.notes.android.data.api.model.UserNoteResponse
import com.lovevery.notes.android.data.api.model.UserNotesResponse
import com.lovevery.notes.android.data.repository.model.UserNoteModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel

fun UserNotesResponse.toUserNotesModel(): UserNotesModel =
    UserNotesModel(
        user = this.user,
        notes = this.notes.map { it.toUserNoteModel() }
    )

fun UserNoteResponse.toUserNoteModel(): UserNoteModel =
    UserNoteModel(
        subject = this.subject,
        message = this.message
    )
