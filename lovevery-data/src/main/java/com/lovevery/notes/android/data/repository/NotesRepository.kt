package com.lovevery.notes.android.data.repository

import com.lovevery.notes.android.data.api.ApiService
import com.lovevery.notes.android.data.api.model.NoteRequest
import com.lovevery.notes.android.data.extensions.applySchedulers
import com.lovevery.notes.android.data.repository.mapper.toNoteModel
import com.lovevery.notes.android.data.repository.mapper.toNotesModel
import com.lovevery.notes.android.data.repository.mapper.toUserNotesModel
import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.data.repository.model.NotesModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel
import io.reactivex.Single
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun addNote(
        subject: String,
        content: String,
    ): Single<NoteModel> =
        apiService
            .addNote(
                NoteRequest(
                    user = "joe", // TODO: Retrieve this value from a manager or a session storage
                    subject = subject,
                    content = content
                )
            )
            .map { it.toNoteModel() }
            .applySchedulers()

    fun getNotes(): Single<NotesModel> {
        return apiService
            .getNotes()
            .map { it.toNotesModel() }
            .applySchedulers()
    }

    fun getUserNotes(userId: String): Single<UserNotesModel> {
        return apiService
            .getNotesForUser(userId)
            .map { it.toUserNotesModel() }
            .applySchedulers()
    }
}
