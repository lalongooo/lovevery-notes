package com.lovevery.notes.android.data.api.model

enum class OperationType(
    val value: String
) {
    ADD_MESSAGE("add_message"),
}

data class NoteRequest(
    val user: String,
    val operation: OperationType,
    val subject: String,
    val message: String
)
