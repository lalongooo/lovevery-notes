package com.lovevery.notes.android.data.repository.model

data class UserNotesModel(
    val notes: Map<String, List<NoteModel>>
)

data class NoteModel(
    val subject: String,
    val content: String
)
