package com.lovevery.notes.android.data.repository

import com.lovevery.notes.android.data.api.ApiService
import com.lovevery.notes.android.data.api.model.NoteRequest
import com.lovevery.notes.android.data.api.model.NoteResponse
import com.lovevery.notes.android.data.extensions.applySchedulers
import com.lovevery.notes.android.data.repository.mapper.toUserNotesModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel
import io.reactivex.Single
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun addNote(noteRequest: NoteRequest): Single<NoteResponse> =
        apiService
            .addNote(noteRequest)
            .applySchedulers()

    fun getNotes(): Single<UserNotesModel> {
        return apiService
            .getNotes()
            .map { it.toUserNotesModel() }
            .applySchedulers()
    }

}