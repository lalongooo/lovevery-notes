package com.lovevery.notes.android.data.api.model

import com.google.gson.annotations.SerializedName

data class NoteResponse(
    val user: String,
    val subject: String,
    @SerializedName("message")
    val content: String,
)
