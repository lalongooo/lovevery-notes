package com.lovevery.notes.android.data.api.model

import com.google.gson.annotations.SerializedName

data class UserNoteResponse(
    val subject: String,
    val message: String
)

data class UserNotesResponse(
    val user: String,
    @SerializedName("message")
    val notes: List<UserNoteResponse>
)
