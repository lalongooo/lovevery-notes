package com.lovevery.notes.android.data.api.model

import com.google.gson.annotations.SerializedName

enum class OperationType(val value: String) {
    ADD_MESSAGE("add_message"),
    ;

    override fun toString(): String = value

    companion object
}

data class NoteRequest(
    val user: String,
    val operation: OperationType = OperationType.ADD_MESSAGE,
    val subject: String,
    @SerializedName("message")
    val content: String,
)
