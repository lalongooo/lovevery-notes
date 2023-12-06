package com.lovevery.notes.android.mapper

import com.lovevery.notes.android.data.repository.model.UserNoteModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel
import com.lovevery.notes.android.model.UserNote
import com.lovevery.notes.android.model.UserNotes

fun UserNoteModel.toUserNote(): UserNote =
    UserNote(subject = this.subject, message = this.message)

fun UserNotesModel.toUserNotes(): UserNotes =
    UserNotes(user = this.user, notes = this.notes.map { it.toUserNote() })
