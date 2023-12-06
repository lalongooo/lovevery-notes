package com.lovevery.notes.android.data.repository.model

data class UserNoteModel(
    val subject: String,
    val message: String
)

data class UserNotesModel(
    val user: String,
    val notes: List<UserNoteModel>
)
