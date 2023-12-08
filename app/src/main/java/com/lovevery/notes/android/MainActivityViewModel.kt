package com.lovevery.notes.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lovevery.notes.android.base.BaseViewModel
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.data.repository.SessionRepository
import com.lovevery.notes.android.extensions.asLiveData
import com.lovevery.notes.android.mapper.toUserNotes
import com.lovevery.notes.android.model.Notes
import com.lovevery.notes.android.ui.NotesState
import com.lovevery.notes.android.ui.UserNotesState
import com.lovevery.notes.android.utility.SingleLiveEvent
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val sessionRepository: SessionRepository
) : BaseViewModel() {

    private val _notesState = SingleLiveEvent<NotesState>()
    val notesState = _notesState.asLiveData()

    private val _notes = MutableLiveData<Notes>()
    val notes: LiveData<Notes> get() = _notes

    private val _userNotes = MutableLiveData<UserNotesState>()
    val userNotes: LiveData<UserNotesState> get() = _userNotes

    private lateinit var selectedSubject: String

    fun setUserNotes(notes: Notes) {
        _notes.value = notes
    }

    fun saveUserId(userId: String) = sessionRepository.saveUserId(userId)

    fun saveSubject(subject: String) = sessionRepository.saveSubject(subject)

    fun getUsers(): List<String> {
        return notes.value
            ?.notes
            ?.keys
            ?.toList()
            ?: run { emptyList() }
    }

    fun getSubjects(): List<String> {
        return notes.value
            ?.notes
            ?.get(sessionRepository.getUserId())
            ?.map { it.subject }
            ?.distinct()
            ?.toList()
            ?: run { emptyList() }
    }


    fun refreshUserNotes() {
        disposable.add(
            notesRepository.getUserNotes(sessionRepository.getUserId())
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
                    }
                )
        )
    }

    companion object {
        private const val TAG = "MainActivityViewModel"
    }
}
