package com.lovevery.notes.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.data.repository.SessionRepository
import com.lovevery.notes.android.data.repository.model.UserNoteModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel
import com.lovevery.notes.android.model.Note
import com.lovevery.notes.android.model.Notes
import com.lovevery.notes.android.ui.UserNotesState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    @RelaxedMockK
    private lateinit var sessionRepository: SessionRepository

    private lateinit var systemUnderTest: MainActivityViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        systemUnderTest = spyk(
            MainActivityViewModel(notesRepository = notesRepository, sessionRepository = sessionRepository)
        )
    }

    @Test
    fun `setUserNotes updates LiveData`() {
        // given
        val notes = Notes(
            mapOf(
                "user1" to listOf(Note(
                    "subject1",
                    "message1")
                ),
                "user2" to listOf(Note(
                    "subject2",
                    "message2"
                )
                )
            )
        )
        val notesObserver: Observer<Notes> = mockk(relaxed = true)
        systemUnderTest.notes.observeForever(notesObserver)

        // when
        systemUnderTest.setUserNotes(notes)

        // verify
        verify { notesObserver.onChanged(notes) }
    }

    @Test
    fun `test saveUserId`() {
        // given
        val testUserId = "user123"
        every { sessionRepository.saveUserId(testUserId) } returns Unit

        // when
        systemUnderTest.saveUserId(testUserId)

        // verify
        verify { sessionRepository.saveUserId(testUserId) }
    }

    @Test
    fun `test saveSubject`() {
        // given
        val testSubject = "testSubject"
        every { sessionRepository.saveSubject(testSubject) } returns Unit

        // when
        systemUnderTest.saveSubject(testSubject)

        // verify
        verify { sessionRepository.saveSubject(testSubject) }
    }

    private val notesLiveData = MutableLiveData<Notes>()

    @Test
    fun `test getUsers with non-empty results`() {
        // given
        notesLiveData.value = Notes(mapOf("user123" to listOf(Note("test", "t"))))
        every { systemUnderTest getProperty "notes" } returns notesLiveData

        // when
        val result = systemUnderTest.getUsers()

        // verify
        assert(result.isNotEmpty())
    }

    @Test
    fun `test getUsers with empty results`() {
        // given
        notesLiveData.value = Notes(emptyMap())
        every { systemUnderTest getProperty "notes" } returns notesLiveData

        // when
        val result = systemUnderTest.getUsers()

        // verify
        assert(result.isEmpty())
    }

    @Test
    fun `test getSubjects with non-empty results`() {
        // given
        val userId = "user123"
        every { sessionRepository.getUserId() } returns userId
        notesLiveData.value = Notes(mapOf(userId to listOf(Note("test", "t"))))
        every { systemUnderTest getProperty "notes" } returns notesLiveData

        // when
        val result = systemUnderTest.getSubjects()

        // verify
        assert(result.isNotEmpty())
    }

    @Test
    fun `test getSubjects with empty results`() {
        // given
        notesLiveData.value = Notes(emptyMap())
        every { systemUnderTest getProperty "notes" } returns notesLiveData

        // when
        val result = systemUnderTest.getSubjects()

        // verify
        assert(result.isEmpty())
    }

    @Test
    fun `test refreshUserNotes with empty results`() {
        // given
        val userId = "user123"
        val mockUserNotesModel: UserNotesModel = mockk(relaxed = true)
        every { sessionRepository.getUserId() } returns userId
        every { notesRepository.getUserNotes(userId) } returns Single.just(mockUserNotesModel)

        val userNotesStateObserver: Observer<UserNotesState> = mockk(relaxed = true)
        systemUnderTest.userNotes.observeForever(userNotesStateObserver)

        // when
        systemUnderTest.refreshUserNotes()

        // verify
        verify { sessionRepository.getUserId() }
        verify { notesRepository.getUserNotes(userId) }
        verify { userNotesStateObserver.onChanged(UserNotesState.Empty) }
    }

    @Test
    fun `test refreshUserNotes success`() {
        // given
        val userId = "user123"
        val mockUserNotesModel = UserNotesModel(
            user = userId,
            notes = listOf(
                UserNoteModel(
                    subject = "testSubject111",
                    message = "testMessage222"
                )
            )
        )
        every { sessionRepository.getUserId() } returns userId
        every { notesRepository.getUserNotes(userId) } returns Single.just(mockUserNotesModel)

        val userNotesStateObserver: Observer<UserNotesState> = mockk(relaxed = true)
        systemUnderTest.userNotes.observeForever(userNotesStateObserver)

        // when
        systemUnderTest.refreshUserNotes()

        // verify
        verify { sessionRepository.getUserId() }
        verify { notesRepository.getUserNotes(userId) }
        verify { userNotesStateObserver.onChanged(ofType(UserNotesState.Success::class)) }
    }

    @Test
    fun `test refreshUserNotes returns an error`() {
        // given
        val userId = "user123"
        val error = Throwable("An error occurred")
        val userNotesStateObserver: Observer<UserNotesState> = mockk(relaxed = true)
        systemUnderTest.userNotes.observeForever(userNotesStateObserver)

        every { sessionRepository.getUserId() } returns userId
        every { notesRepository.getUserNotes(userId) } returns Single.error(error)

        // when
        systemUnderTest.refreshUserNotes()

        // verify
        verify { userNotesStateObserver.onChanged(UserNotesState.Error(error)) }
    }
}