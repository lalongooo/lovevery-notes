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
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionRepository: SessionRepository
) {
    fun addNote(
        subject: String,
        content: String,
    ): Single<NoteModel> =
        apiService
            .addNote(
                NoteRequest(
                    user = sessionRepository.getUserId(),
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

    fun getUserNotesBySubject(): Single<UserNotesModel> {
        return apiService
            .getNotesForUser(sessionRepository.getUserId())
            .map { it.toUserNotesModel() }
            .map { userNotesModel ->
                userNotesModel.notes
                    .filter { it.subject == sessionRepository.getSubject() }
                    .let { UserNotesModel(userNotesModel.user, it) }
            }
            .applySchedulers()
    }
}
