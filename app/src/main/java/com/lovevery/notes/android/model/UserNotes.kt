package com.lovevery.notes.android.model

data class UserNote(
    val subject: String,
    val message: String
)

data class UserNotes(
    val user: String,
    val notes: List<UserNote>
)
