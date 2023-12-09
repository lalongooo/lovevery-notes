package com.lovevery.notes.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.data.repository.SessionRepository
import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.data.repository.model.NotesModel
import com.lovevery.notes.android.data.repository.model.UserNoteModel
import com.lovevery.notes.android.data.repository.model.UserNotesModel
import com.lovevery.notes.android.model.Note
import com.lovevery.notes.android.model.Notes
import com.lovevery.notes.android.ui.NotesState
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

    @Test
    fun `test getUsers with non-empty results`() {
        // given
        val testNotes = Notes(
            mapOf("joe" to listOf(Note(subject = "cars", content = "Porsche 911")))
        )
        val notesLiveData = MutableLiveData<NotesState>()
        notesLiveData.value = NotesState.Success(testNotes)
        every { systemUnderTest getProperty "notesState" } returns notesLiveData

        // when
        val result = systemUnderTest.getUsers()

        // verify
        assert(result.isNotEmpty())
    }

    @Test
    fun `test getUsers with empty results`() {
        // given
        val notesLiveData = MutableLiveData<NotesState>()
        notesLiveData.value = NotesState.Empty
        every { systemUnderTest getProperty "notesState" } returns notesLiveData

        // when
        val result = systemUnderTest.getUsers()

        // verify
        assert(result.isEmpty())
    }

    @Test
    fun `test getSubjects with non-empty results`() {
        // given
        val testNotes = Notes(
            mapOf("joe" to listOf(Note(subject = "cars", content = "Porsche 911")))
        )
        val notesLiveData = MutableLiveData<NotesState>()
        notesLiveData.value = NotesState.Success(testNotes)
        every { sessionRepository.getUserId() } returns "joe"
        every { systemUnderTest getProperty "notesState" } returns notesLiveData

        // when
        val result = systemUnderTest.getSubjects()

        // verify
        assert(result.isNotEmpty())
    }

    @Test
    fun `test getSubjects with empty results`() {
        // given
        val notesLiveData = MutableLiveData<NotesState>()
        notesLiveData.value = NotesState.Empty
        every { systemUnderTest getProperty "notesState" } returns notesLiveData

        // when
        val result = systemUnderTest.getSubjects()

        // verify
        assert(result.isEmpty())
    }

    @Test
    fun `test refreshUserNotes with empty results`() {
        // given
        val mockUserNotesModel: UserNotesModel = mockk(relaxed = true)
        every { notesRepository.getUserNotesBySubject() } returns Single.just(mockUserNotesModel)

        val userNotesStateObserver: Observer<UserNotesState> = mockk(relaxed = true)
        systemUnderTest.userNotes.observeForever(userNotesStateObserver)

        // when
        systemUnderTest.refreshUserNotes()

        // verify
        verify { notesRepository.getUserNotesBySubject() }
        verify { userNotesStateObserver.onChanged(ofType(UserNotesState.Empty::class)) }
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
        every {
            notesRepository.getUserNotesBySubject()
        } returns Single.just(mockUserNotesModel)

        val userNotesStateObserver: Observer<UserNotesState> = mockk(relaxed = true)
        systemUnderTest.userNotes.observeForever(userNotesStateObserver)

        // when
        systemUnderTest.refreshUserNotes()

        // verify
        verify { notesRepository.getUserNotesBySubject() }
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
        every { notesRepository.getUserNotesBySubject() } returns Single.error(error)

        // when
        systemUnderTest.refreshUserNotes()

        // verify
        verify { userNotesStateObserver.onChanged(UserNotesState.Error(error)) }
    }


    @Test
    fun `test getNotes returns success`() {
        // given
        val testUser = "user123"
        val testSubject = "testSubject"
        val testContent = "testContent"
        val mockNotes = NotesModel(
            notes = mapOf(testUser to listOf(NoteModel(
                subject = testSubject,
                content = testContent,
            )))
        )
        val notesStateObserver: Observer<NotesState> = mockk(relaxed = true)
        systemUnderTest.notesState.observeForever(notesStateObserver)

        every { notesRepository.getNotes() } returns Single.just(mockNotes)

        // when
        systemUnderTest.getAllNotes()

        // verify
        verify { notesRepository.getNotes() }
        verify { notesStateObserver.onChanged(ofType(NotesState.Success::class)) }
    }

    @Test
    fun `test getNotes returns an empty response`() {
        // given
        val mockNotes = NotesModel(emptyMap())
        val notesStateObserver: Observer<NotesState> = mockk(relaxed = true)
        systemUnderTest.notesState.observeForever(notesStateObserver)

        every { notesRepository.getNotes() } returns Single.just(mockNotes)

        // when
        systemUnderTest.getAllNotes()

        // verify
        verify { notesRepository.getNotes() }
        verify { notesStateObserver.onChanged(ofType(NotesState.Empty::class)) }
    }

    @Test
    fun `test getNotes results in an error state`() {
        // Arrange
        val error = Throwable("An error occurred")
        val observer = mockk<Observer<NotesState>>()
        systemUnderTest.notesState.observeForever(observer)

        every { notesRepository.getNotes() } returns Single.error(error)

        // Act
        systemUnderTest.getAllNotes()

        // Assert
        verify { notesRepository.getNotes() }
        verify { observer.onChanged(NotesState.Error(error)) }
    }
}