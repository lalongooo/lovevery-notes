package com.lovevery.notes.android.model

data class Notes(
    val notes: Map<String, List<Note>>
)

data class Note(
    val subject: String,
    val content: String
)