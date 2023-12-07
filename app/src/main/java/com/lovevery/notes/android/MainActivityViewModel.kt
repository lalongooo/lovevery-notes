package com.lovevery.notes.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lovevery.notes.android.base.BaseViewModel
import com.lovevery.notes.android.model.Notes
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    private val _userNotes = MutableLiveData<Notes>()
    val userNotes: LiveData<Notes> get() = _userNotes

    private lateinit var userId: String
    private lateinit var selectedSubject: String

    fun setUserNotes(notes: Notes) {
        _userNotes.value = notes
    }

    fun setUsername(userId: String) {
        this.userId = userId
    }

    fun setSubject(selectedSubject: String) {
        this.selectedSubject = selectedSubject
    }

    fun getUsers(): List<String> {
        return userNotes.value
            ?.notes
            ?.keys
            ?.toList()
            ?: run { emptyList() }
    }

    fun getSubjects(): List<String> {
        return userNotes.value
            ?.notes
            ?.get(userId)
            ?.map { it.subject }
            ?.distinct()
            ?.toList()
            ?: run { emptyList() }
    }

    companion object {
        private const val TAG = "MainActivityViewModel"
    }
}
