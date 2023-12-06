package com.lovevery.notes.android.data.api.model

import com.google.gson.annotations.SerializedName

data class NotesResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("body")
    val notes: Map<String, List<NoteModelResponse>>
)

data class NoteModelResponse(
    val subject: String,
    @SerializedName("message")
    val content: String,
)
