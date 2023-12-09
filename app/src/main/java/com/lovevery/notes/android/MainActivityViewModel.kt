package com.lovevery.notes.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lovevery.notes.android.base.BaseViewModel
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.data.repository.SessionRepository
import com.lovevery.notes.android.extensions.asLiveData
import com.lovevery.notes.android.mapper.toNotes
import com.lovevery.notes.android.mapper.toUserNotes
import com.lovevery.notes.android.ui.NotesState
import com.lovevery.notes.android.ui.UserNotesState
import com.lovevery.notes.android.utility.SingleLiveEvent
import javax.inject.Inject

class MainActivityViewModel
    @Inject
    constructor(
        private val notesRepository: NotesRepository,
        private val sessionRepository: SessionRepository,
    ) : BaseViewModel() {
        private val _userNotes = SingleLiveEvent<UserNotesState>()
        val userNotes = _userNotes.asLiveData()

        private val _notesState = MutableLiveData<NotesState>()
        val notesState: LiveData<NotesState> get() = _notesState

        fun saveUserId(userId: String) = sessionRepository.saveUserId(userId)

        fun saveSubject(subject: String) = sessionRepository.saveSubject(subject)

        fun getUsers(): List<String> {
            val stateSuccess = notesState.value as? NotesState.Success
            return stateSuccess?.notes
                ?.notes
                ?.keys
                ?.toList()
                ?: run { emptyList() }
        }

        fun getSubjects(): List<String> {
            val stateSuccess = notesState.value as? NotesState.Success
            return stateSuccess?.notes
                ?.notes
                ?.get(sessionRepository.getUserId())
                ?.map { it.subject }
                ?.distinct()
                ?.toList()
                ?: run { emptyList() }
        }

        fun refreshUserNotes() {
            disposable.add(
                notesRepository.getUserNotesBySubject()
                    .map { it.toUserNotes() }
                    .doOnSubscribe { progress.value = true }
                    .doFinally { progress.value = false }
                    .subscribe(
                        { notes ->
                            if (notes.notes.isEmpty()) {
                                _userNotes.value = UserNotesState.Empty
                            } else {
                                _userNotes.value = UserNotesState.Success(notes)
                            }
                        },
                        {
                            _userNotes.value = UserNotesState.Error(it)
                        },
                    ),
            )
        }

        fun getAllNotes() {
            disposable.add(
                notesRepository.getNotes()
                    .map { it.toNotes() }
                    .doOnSubscribe { progress.value = true }
                    .doFinally { progress.value = false }
                    .subscribe(
                        { notes ->
                            if (notes.notes.isEmpty()) {
                                _notesState.value = NotesState.Empty
                            } else {
                                _notesState.value = NotesState.Success(notes)
                            }
                        },
                        {
                            _notesState.value = NotesState.Error(it)
                        },
                    ),
            )
        }

        companion object {
            private const val TAG = "MainActivityViewModel"
        }
    }
